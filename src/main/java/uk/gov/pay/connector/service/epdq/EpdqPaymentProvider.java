package uk.gov.pay.connector.service.epdq;

import io.dropwizard.setup.Environment;
import uk.gov.pay.connector.app.GatewayConfig;
import uk.gov.pay.connector.model.CancelGatewayRequest;
import uk.gov.pay.connector.model.CaptureGatewayRequest;
import uk.gov.pay.connector.model.GatewayError;
import uk.gov.pay.connector.model.GatewayRequest;
import uk.gov.pay.connector.model.RefundGatewayRequest;
import uk.gov.pay.connector.model.api.ExternalChargeRefundAvailability;
import uk.gov.pay.connector.model.domain.ChargeEntity;
import uk.gov.pay.connector.model.gateway.Auth3dsResponseGatewayRequest;
import uk.gov.pay.connector.model.gateway.AuthorisationGatewayRequest;
import uk.gov.pay.connector.model.gateway.GatewayResponse;
import uk.gov.pay.connector.service.BaseAuthoriseResponse;
import uk.gov.pay.connector.service.BaseCancelResponse;
import uk.gov.pay.connector.service.BaseResponse;
import uk.gov.pay.connector.service.ExternalRefundAvailabilityCalculator;
import uk.gov.pay.connector.service.GatewayClient;
import uk.gov.pay.connector.service.GatewayClientFactory;
import uk.gov.pay.connector.service.GatewayOperation;
import uk.gov.pay.connector.service.GatewayOperationClientBuilder;
import uk.gov.pay.connector.service.GatewayOrder;
import uk.gov.pay.connector.service.PaymentGatewayName;
import uk.gov.pay.connector.service.PaymentProviderOperations;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Invocation;
import java.util.EnumMap;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static fj.data.Either.reduce;
import static uk.gov.pay.connector.model.ErrorType.GENERIC_GATEWAY_ERROR;
import static uk.gov.pay.connector.model.domain.GatewayAccount.CREDENTIALS_MERCHANT_ID;
import static uk.gov.pay.connector.model.domain.GatewayAccount.CREDENTIALS_PASSWORD;
import static uk.gov.pay.connector.model.domain.GatewayAccount.CREDENTIALS_SHA_IN_PASSPHRASE;
import static uk.gov.pay.connector.model.domain.GatewayAccount.CREDENTIALS_USERNAME;
import static uk.gov.pay.connector.service.GatewayOperation.AUTHORISE;
import static uk.gov.pay.connector.service.GatewayOperation.CANCEL;
import static uk.gov.pay.connector.service.GatewayOperation.CAPTURE;
import static uk.gov.pay.connector.service.GatewayOperation.REFUND;
import static uk.gov.pay.connector.service.PaymentGatewayName.EPDQ;
import static uk.gov.pay.connector.service.epdq.EpdqOrderRequestBuilder.anEpdqAuthoriseOrderRequestBuilder;
import static uk.gov.pay.connector.service.epdq.EpdqOrderRequestBuilder.anEpdqCancelOrderRequestBuilder;
import static uk.gov.pay.connector.service.epdq.EpdqOrderRequestBuilder.anEpdqCaptureOrderRequestBuilder;
import static uk.gov.pay.connector.service.epdq.EpdqOrderRequestBuilder.anEpdqRefundOrderRequestBuilder;


public class EpdqPaymentProvider implements PaymentProviderOperations {

    public static final String ROUTE_FOR_NEW_ORDER = "orderdirect.asp";
    public static final String ROUTE_FOR_MAINTENANCE_ORDER = "maintenancedirect.asp";

    private Environment environment;
    private GatewayConfig config;
    private GatewayClientFactory gatewayClientFactory;
    protected ExternalRefundAvailabilityCalculator externalRefundAvailabilityCalculator;
    protected EnumMap<GatewayOperation, GatewayClient> gatewayOperationClientMap;

    @Inject
    public EpdqPaymentProvider(
            @Named("EPDQ") GatewayConfig config,
            GatewayClientFactory gatewayClientFactory,
            ExternalRefundAvailabilityCalculator externalRefundAvailabilityCalculator,
            Environment environment) {

        this.config = config;
        this.gatewayClientFactory = gatewayClientFactory;
        this.externalRefundAvailabilityCalculator = externalRefundAvailabilityCalculator;
        this.environment = environment;
        this.gatewayOperationClientMap = GatewayOperationClientBuilder.builder()
                .authClient(gatewayClientForOperation(EPDQ, AUTHORISE, EpdqPaymentProvider.includeSessionIdentifier()))
                .cancelClient(gatewayClientForOperation(EPDQ, CANCEL, EpdqPaymentProvider.includeSessionIdentifier()))
                .captureClient(gatewayClientForOperation(EPDQ, CAPTURE, EpdqPaymentProvider.includeSessionIdentifier()))
                .refundClient(gatewayClientForOperation(EPDQ, REFUND, EpdqPaymentProvider.includeSessionIdentifier()))
                .build();
    }

    public PaymentGatewayName getPaymentGatewayName() {
        return PaymentGatewayName.EPDQ;
    }

    public Optional<String> generateTransactionId() {
        return Optional.empty();
    }

    public GatewayResponse<EpdqAuthorisationResponse> authorise(AuthorisationGatewayRequest request) {
        return sendReceive(ROUTE_FOR_NEW_ORDER, request, buildAuthoriseOrderFor(), EpdqAuthorisationResponse.class, extractResponseIdentifier());
    }

    public GatewayResponse<BaseAuthoriseResponse> authorise3dsResponse(Auth3dsResponseGatewayRequest request) {
        return GatewayResponse.with(new GatewayError("3D Secure not implemented for Epdq", GENERIC_GATEWAY_ERROR));
    }

    public GatewayResponse<EpdqCaptureResponse> capture(CaptureGatewayRequest request) {
        return sendReceive(ROUTE_FOR_MAINTENANCE_ORDER, request, buildCaptureOrderFor(), EpdqCaptureResponse.class, extractResponseIdentifier());
    }

    public GatewayResponse refund(RefundGatewayRequest request) {
        return sendReceive(ROUTE_FOR_MAINTENANCE_ORDER, request, buildRefundOrderFor(), EpdqRefundResponse.class, extractResponseIdentifier());
    }

    public GatewayResponse cancel(CancelGatewayRequest request) {
        return sendReceive(ROUTE_FOR_MAINTENANCE_ORDER, request, buildCancelOrderFor(), EpdqCancelResponse.class, extractResponseIdentifier());
    }

    public ExternalChargeRefundAvailability getExternalChargeRefundAvailability(ChargeEntity chargeEntity) {
        return externalRefundAvailabilityCalculator.calculate(chargeEntity);
    }

    private Function<AuthorisationGatewayRequest, GatewayOrder> buildAuthoriseOrderFor() {
        return request -> anEpdqAuthoriseOrderRequestBuilder()
                .withOrderId(request.getChargeExternalId())
                .withPassword(request.getGatewayAccount().getCredentials().get(CREDENTIALS_PASSWORD))
                .withShaInPassphrase(request.getGatewayAccount().getCredentials().get(
                    CREDENTIALS_SHA_IN_PASSPHRASE))
                .withUserId(request.getGatewayAccount().getCredentials().get(CREDENTIALS_USERNAME))
                .withMerchantCode(request.getGatewayAccount().getCredentials().get(CREDENTIALS_MERCHANT_ID))
                .withDescription(request.getDescription())
                .withAmount(request.getAmount())
                .withAuthorisationDetails(request.getAuthCardDetails())
                .build();
    }

    private Function<CaptureGatewayRequest, GatewayOrder> buildCaptureOrderFor() {
        return request -> anEpdqCaptureOrderRequestBuilder()
                .withUserId(request.getGatewayAccount().getCredentials().get(CREDENTIALS_USERNAME))
                .withPassword(request.getGatewayAccount().getCredentials().get(CREDENTIALS_PASSWORD))
                .withShaInPassphrase(request.getGatewayAccount().getCredentials().get(
                    CREDENTIALS_SHA_IN_PASSPHRASE))
                .withMerchantCode(request.getGatewayAccount().getCredentials().get(CREDENTIALS_MERCHANT_ID))
                .withTransactionId(request.getTransactionId())
                .build();
    }

    private Function<CancelGatewayRequest, GatewayOrder> buildCancelOrderFor() {
        return request -> anEpdqCancelOrderRequestBuilder()
                .withUserId(request.getGatewayAccount().getCredentials().get(CREDENTIALS_USERNAME))
                .withPassword(request.getGatewayAccount().getCredentials().get(CREDENTIALS_PASSWORD))
                .withShaInPassphrase(request.getGatewayAccount().getCredentials().get(
                    CREDENTIALS_SHA_IN_PASSPHRASE))
                .withMerchantCode(request.getGatewayAccount().getCredentials().get(CREDENTIALS_MERCHANT_ID))
                .withTransactionId(request.getTransactionId())
                .build();
    }

    private Function<RefundGatewayRequest, GatewayOrder> buildRefundOrderFor() {
        return request -> anEpdqRefundOrderRequestBuilder()
                .withUserId(request.getGatewayAccount().getCredentials().get(CREDENTIALS_USERNAME))
                .withPassword(request.getGatewayAccount().getCredentials().get(CREDENTIALS_PASSWORD))
                .withShaInPassphrase(request.getGatewayAccount().getCredentials().get(
                        CREDENTIALS_SHA_IN_PASSPHRASE))
                .withMerchantCode(request.getGatewayAccount().getCredentials().get(CREDENTIALS_MERCHANT_ID))
                .withTransactionId(request.getTransactionId())
                .withAmount(request.getAmount())
                .build();
    }

    private Function<GatewayClient.Response, Optional<String>> extractResponseIdentifier() {
        return response -> {
            Optional<String> emptyResponseIdentifierForEpdq = Optional.empty();
            return emptyResponseIdentifierForEpdq;
        };
    }

    public static BiFunction<GatewayOrder, Invocation.Builder, Invocation.Builder> includeSessionIdentifier() {
        return (order, builder) -> builder;
    }

    protected <U extends GatewayRequest> GatewayResponse sendReceive(U request, Function<U, GatewayOrder> order,
                                                                     Class<? extends BaseResponse> clazz,
                                                                     Function<GatewayClient.Response, Optional<String>> responseIdentifier) {

        return sendReceive(null, request, order, clazz, responseIdentifier);
    }

    protected <U extends GatewayRequest> GatewayResponse sendReceive(String route, U request, Function<U, GatewayOrder> order,
                                                                     Class<? extends BaseResponse> clazz,
                                                                     Function<GatewayClient.Response, Optional<String>> responseIdentifier) {
        GatewayClient gatewayClient = gatewayOperationClientMap.get(request.getRequestType());
        return reduce(
                gatewayClient
                        .postRequestFor(route, request.getGatewayAccount(), order.apply(request))
                        .bimap(
                                GatewayResponse::with,
                                r -> mapToResponse(r, clazz, responseIdentifier, gatewayClient)
                        )
        );
    }

    private GatewayResponse mapToResponse(GatewayClient.Response response,
                                          Class<? extends BaseResponse> clazz,
                                          Function<GatewayClient.Response, Optional<String>> responseIdentifier,
                                          GatewayClient client) {
        GatewayResponse.GatewayResponseBuilder<BaseResponse> responseBuilder = GatewayResponse.GatewayResponseBuilder.responseBuilder();

        reduce(
                client.unmarshallResponse(response, clazz)
                        .bimap(
                                responseBuilder::withGatewayError,
                                responseBuilder::withResponse
                        )
        );

        responseIdentifier.apply(response)
                .ifPresent(responseBuilder::withSessionIdentifier);

        return responseBuilder.build();

    }


    private GatewayClient gatewayClientForOperation(PaymentGatewayName gateway,
                                                    GatewayOperation operation,
                                                    BiFunction<GatewayOrder, Invocation.Builder, Invocation.Builder> sessionIdentifier) {
        return gatewayClientFactory.createGatewayClient(
                gateway, operation, config.getUrls(), sessionIdentifier, environment.metrics()
        );
    }
}
