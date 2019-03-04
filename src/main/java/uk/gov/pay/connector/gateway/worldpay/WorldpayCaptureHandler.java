package uk.gov.pay.connector.gateway.worldpay;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import uk.gov.pay.connector.gateway.CaptureHandler;
import uk.gov.pay.connector.gateway.CaptureResponse;
import uk.gov.pay.connector.gateway.GatewayClient;
import uk.gov.pay.connector.gateway.GatewayErrorException;
import uk.gov.pay.connector.gateway.GatewayOrder;
import uk.gov.pay.connector.gateway.model.request.CaptureGatewayRequest;

import java.net.URI;
import java.util.Map;

import static uk.gov.pay.connector.gateway.CaptureResponse.ChargeState.PENDING;
import static uk.gov.pay.connector.gateway.GatewayResponseUnmarshaller.unmarshallResponse;
import static uk.gov.pay.connector.gateway.util.AuthUtil.getGatewayAccountCredentialsAsAuthHeader;
import static uk.gov.pay.connector.gateway.worldpay.WorldpayOrderRequestBuilder.aWorldpayCaptureOrderRequestBuilder;
import static uk.gov.pay.connector.gatewayaccount.model.GatewayAccount.CREDENTIALS_MERCHANT_ID;

public class WorldpayCaptureHandler implements CaptureHandler {

    private final GatewayClient client;
    private final Map<String, URI> gatewayUrlMap;

    public WorldpayCaptureHandler(GatewayClient client, Map<String, URI> gatewayUrlMap) {
        this.client = client;
        this.gatewayUrlMap = gatewayUrlMap;
    }

    @Override
    public CaptureResponse capture(CaptureGatewayRequest request) {
        try {
            GatewayClient.Response response = client.postRequestFor(
                    gatewayUrlMap.get(request.getGatewayAccount().getType()), 
                    request.getGatewayAccount(), 
                    buildCaptureOrder(request),
                    getGatewayAccountCredentialsAsAuthHeader(request.getGatewayAccount()));
            return CaptureResponse.fromBaseCaptureResponse(unmarshallResponse(response, WorldpayCaptureResponse.class), PENDING);
        } catch (GatewayErrorException e) {
            return CaptureResponse.fromGatewayError(e.toGatewayError());
        }
     }

    private GatewayOrder buildCaptureOrder(CaptureGatewayRequest request) {
        return aWorldpayCaptureOrderRequestBuilder()
                .withDate(DateTime.now(DateTimeZone.UTC))
                .withMerchantCode(request.getGatewayAccount().getCredentials().get(CREDENTIALS_MERCHANT_ID))
                .withAmount(request.getAmount())
                .withTransactionId(request.getTransactionId())
                .build();
    }
}
