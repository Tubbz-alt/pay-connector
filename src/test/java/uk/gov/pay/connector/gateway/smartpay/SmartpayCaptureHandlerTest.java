package uk.gov.pay.connector.gateway.smartpay;

import com.google.common.collect.ImmutableMap;
import fj.data.Either;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.pay.connector.charge.model.domain.ChargeEntity;
import uk.gov.pay.connector.gateway.GatewayClient;
import uk.gov.pay.connector.gateway.GatewayOrder;
import uk.gov.pay.connector.gateway.model.GatewayError;
import uk.gov.pay.connector.gateway.model.request.CaptureGatewayRequest;
import uk.gov.pay.connector.gateway.model.response.BaseCaptureResponse;
import uk.gov.pay.connector.gateway.model.response.GatewayResponse;
import uk.gov.pay.connector.gatewayaccount.model.GatewayAccountEntity;
import uk.gov.pay.connector.util.TestTemplateResourceLoader;

import javax.ws.rs.core.Response;

import static fj.data.Either.right;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static uk.gov.pay.connector.gatewayaccount.model.GatewayAccountEntity.Type.TEST;
import static uk.gov.pay.connector.model.domain.ChargeEntityFixture.aValidChargeEntity;
import static uk.gov.pay.connector.util.TestTemplateResourceLoader.SMARTPAY_CAPTURE_SUCCESS_RESPONSE;

@RunWith(MockitoJUnitRunner.class)
public class SmartpayCaptureHandlerTest {

    private SmartpayCaptureHandler smartpayCaptureHandler;

    @Mock
    private GatewayClient client;
    @Mock
    private Response response;

    @Before
    public void setup() {
        smartpayCaptureHandler = new SmartpayCaptureHandler(client);
    }

    @Test
    public void shouldCaptureAPaymentSuccessfully() {
        //mock client.postRequestFor
        when(response.getStatus()).thenReturn(HttpStatus.SC_OK);
        when(response.readEntity(String.class)).thenReturn(successCaptureResponse());
        Either<GatewayError, GatewayClient.Response> response = right(new TestResponse(this.response));
        when(client.postRequestFor(isNull(), any(GatewayAccountEntity.class), any(GatewayOrder.class))).thenReturn(response);
        
        //mock client.unmarshallResponse
        Either<GatewayError, SmartpayCaptureResponse> unmarshalledResponse = right(new SmartpayCaptureResponse());
        when(client.unmarshallResponse(any(TestResponse.class), any(Class.class))).thenReturn(unmarshalledResponse);
        
        ChargeEntity chargeEntity = aValidChargeEntity()
                .withGatewayAccountEntity(aServiceAccount())
                .build();

        CaptureGatewayRequest request = CaptureGatewayRequest.valueOf(chargeEntity);
        GatewayResponse gatewayResponse = smartpayCaptureHandler.capture(request);
        assertTrue(gatewayResponse.isSuccessful());
    }

    private String successCaptureResponse() {
        return TestTemplateResourceLoader.load(SMARTPAY_CAPTURE_SUCCESS_RESPONSE).replace("{{pspReference}}", "8614440510830227");
    }

    private GatewayAccountEntity aServiceAccount() {
        GatewayAccountEntity gatewayAccount = new GatewayAccountEntity();
        gatewayAccount.setId(1L);
        gatewayAccount.setGatewayName("smartpay");
        gatewayAccount.setCredentials(ImmutableMap.of(
                "username", "theUsername",
                "password", "thePassword",
                "merchant_id", "theMerchantCode"
        ));
        gatewayAccount.setType(TEST);

        return gatewayAccount;
    }
    
    private class TestResponse extends GatewayClient.Response {

        protected TestResponse(Response delegate) {
            super(delegate);
        }
    }
}
