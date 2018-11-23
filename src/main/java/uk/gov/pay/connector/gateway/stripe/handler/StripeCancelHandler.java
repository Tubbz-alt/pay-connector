package uk.gov.pay.connector.gateway.stripe.handler;

import com.google.common.collect.ImmutableMap;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.connector.app.StripeGatewayConfig;
import uk.gov.pay.connector.gateway.model.GatewayError;
import uk.gov.pay.connector.gateway.model.request.CancelGatewayRequest;
import uk.gov.pay.connector.gateway.model.response.BaseCancelResponse;
import uk.gov.pay.connector.gateway.model.response.BaseResponse;
import uk.gov.pay.connector.gateway.model.response.GatewayResponse;
import uk.gov.pay.connector.gateway.stripe.DownstreamException;
import uk.gov.pay.connector.gateway.stripe.GatewayClientException;
import uk.gov.pay.connector.gateway.stripe.GatewayException;
import uk.gov.pay.connector.gateway.stripe.StripeGatewayClient;
import uk.gov.pay.connector.gateway.stripe.json.StripeErrorResponse;
import uk.gov.pay.connector.gatewayaccount.model.GatewayAccountEntity;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.singletonList;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE;
import static uk.gov.pay.connector.gateway.model.GatewayError.genericGatewayError;
import static uk.gov.pay.connector.gateway.model.GatewayError.unexpectedStatusCodeFromGateway;
import static uk.gov.pay.connector.gateway.stripe.util.StripeAuthUtil.getAuthHeaderValue;

public class StripeCancelHandler {

    private static final Logger logger = LoggerFactory.getLogger(StripeCancelHandler.class);

    private final StripeGatewayClient client;
    private final StripeGatewayConfig stripeGatewayConfig;

    public StripeCancelHandler(StripeGatewayClient client, StripeGatewayConfig stripeGatewayConfig) {
        this.client = client;
        this.stripeGatewayConfig = stripeGatewayConfig;
    }

    public GatewayResponse<BaseCancelResponse> cancel(CancelGatewayRequest request) {
        String url = stripeGatewayConfig.getUrl() + "/v1/refunds";
        GatewayAccountEntity gatewayAccount = request.getGatewayAccount();

        GatewayResponse.GatewayResponseBuilder<BaseResponse> responseBuilder = GatewayResponse.GatewayResponseBuilder.responseBuilder();
        
        try {
            client.postRequest(
                    URI.create(url),
                    URLEncodedUtils.format(singletonList(new BasicNameValuePair("charge", request.getTransactionId())), defaultCharset()),
                    ImmutableMap.of(AUTHORIZATION, getAuthHeaderValue(stripeGatewayConfig)),
                    APPLICATION_FORM_URLENCODED_TYPE,
                    format("gateway-operations.%s.%s.capture", gatewayAccount.getGatewayName(), gatewayAccount.getType()));
            
            return responseBuilder.withResponse(new BaseResponse() {
                @Override
                public String getErrorCode() {
                    return null;
                }

                @Override
                public String getErrorMessage() {
                    return null;
                }
            }).build();
            
        } catch (GatewayClientException e) {
            
            Response response = e.getResponse();
            StripeErrorResponse stripeErrorResponse = response.readEntity(StripeErrorResponse.class);
            String errorId = UUID.randomUUID().toString();
            logger.error("Cancel failed for gateway transaction id {}. Failure code from Stripe: {}, failure message from Stripe: {}. ErrorId: {}. Response code from Stripe: {}",
                    request.getTransactionId(), stripeErrorResponse.getError().getCode(), stripeErrorResponse.getError().getMessage(), errorId, response.getStatus());
            GatewayError gatewayError = genericGatewayError(stripeErrorResponse.getError().getMessage());
            return responseBuilder.withGatewayError(gatewayError).build();

        } catch (GatewayException e) {
            
            return responseBuilder.withGatewayError(GatewayError.of(e)).build();
            
        } catch (DownstreamException e) {
            
            String errorId = UUID.randomUUID().toString();
            logger.error("Cancel failed for transaction id {}. Reason: {}. Status code from Stripe: {}. ErrorId: {}",
                    request.getTransactionId(), e.getMessage(), e.getStatusCode(), errorId);
            GatewayError gatewayError = unexpectedStatusCodeFromGateway("An internal server error occurred. ErrorId: " + errorId);
            return responseBuilder.withGatewayError(gatewayError).build();
        }
    }
}