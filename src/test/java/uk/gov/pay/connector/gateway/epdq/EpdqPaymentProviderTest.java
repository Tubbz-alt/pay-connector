package uk.gov.pay.connector.gateway.epdq;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.pay.connector.gateway.model.GatewayError;
import uk.gov.pay.connector.gateway.model.response.BaseAuthoriseResponse;
import uk.gov.pay.connector.gateway.model.response.BaseCancelResponse;
import uk.gov.pay.connector.gateway.model.response.BaseRefundResponse;
import uk.gov.pay.connector.gateway.model.response.GatewayResponse;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static uk.gov.pay.connector.gateway.model.ErrorType.UNEXPECTED_HTTP_STATUS_CODE_FROM_GATEWAY;

@RunWith(MockitoJUnitRunner.class)
public class EpdqPaymentProviderTest extends BaseEpdqPaymentProviderTest {

    @Test
    public void shouldGetPaymentProviderName() {
        assertThat(provider.getPaymentGatewayName().getName(), is("epdq"));
    }

    @Test
    public void shouldGenerateNoTransactionId() {
        Assert.assertThat(provider.generateTransactionId().isPresent(), is(false));
    }

    @Test
    public void shouldAuthorise() {
        mockPaymentProviderResponse(200, successAuthResponse());
        GatewayResponse<BaseAuthoriseResponse> response = provider.authorise(buildTestAuthorisationRequest());
        verifyPaymentProviderRequest(successAuthRequest());
        assertTrue(response.isSuccessful());
        assertThat(response.getBaseResponse().get().getTransactionId(), is("3014644340"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_IfTryingToAuthoriseAnApplePayPayment() {
        provider.authoriseApplePay(null);
    }

    @Test
    public void shouldNotAuthoriseIfPaymentProviderReturnsUnexpectedStatusCode() {
        mockPaymentProviderResponse(200, errorAuthResponse());
        GatewayResponse<BaseAuthoriseResponse> response = provider.authorise(buildTestAuthorisationRequest());
        assertThat(response.isFailed(), is(true));
        assertThat(response.getGatewayError().isPresent(), is(true));
    }

    @Test
    public void shouldNotAuthoriseIfPaymentProviderReturnsNon200HttpStatusCode() {
        mockPaymentProviderResponse(400, errorAuthResponse());
        GatewayResponse<BaseAuthoriseResponse> response = provider.authorise(buildTestAuthorisationRequest());
        assertThat(response.isFailed(), is(true));
        assertThat(response.getGatewayError().isPresent(), is(true));
        assertEquals(response.getGatewayError().get(), new GatewayError("Unexpected HTTP status code 400 from gateway",
                UNEXPECTED_HTTP_STATUS_CODE_FROM_GATEWAY));
    }

    @Test
    public void shouldCancel() {
        mockPaymentProviderResponse(200, successCancelResponse());
        GatewayResponse<BaseCancelResponse> response = provider.cancel(buildTestCancelRequest());
        verifyPaymentProviderRequest(successCancelRequest());
        assertTrue(response.isSuccessful());
        assertThat(response.getBaseResponse().get().getTransactionId(), is("3014644340"));
    }

    @Test
    public void shouldNotCancelIfPaymentProviderReturnsUnexpectedStatusCode() {
        mockPaymentProviderResponse(200, errorCancelResponse());
        GatewayResponse<BaseCancelResponse> response = provider.cancel(buildTestCancelRequest());
        assertThat(response.isFailed(), is(true));
        assertThat(response.getGatewayError().isPresent(), is(true));
    }

    @Test
    public void shouldNotCancelIfPaymentProviderReturnsNon200HttpStatusCode() {
        mockPaymentProviderResponse(400, errorCancelResponse());
        GatewayResponse<BaseCancelResponse> response = provider.cancel(buildTestCancelRequest());
        assertThat(response.isFailed(), is(true));
        assertThat(response.getGatewayError().isPresent(), is(true));
        assertEquals(response.getGatewayError().get(), new GatewayError("Unexpected HTTP status code 400 from gateway",
                UNEXPECTED_HTTP_STATUS_CODE_FROM_GATEWAY));
    }

    @Test
    public void shouldRefund() {
        mockPaymentProviderResponse(200, successRefundResponse());
        GatewayResponse<BaseRefundResponse> response = provider.refund(buildTestRefundRequest());
        verifyPaymentProviderRequest(successRefundRequest());
        assertTrue(response.isSuccessful());
        assertThat(response.getBaseResponse().get().getReference(), is(Optional.of("3014644340/1")));
    }

    @Test
    public void shouldRefundWithPaymentDeletion() {
        mockPaymentProviderResponse(200, successDeletionResponse());
        GatewayResponse<BaseRefundResponse> response = provider.refund(buildTestRefundRequest());
        verifyPaymentProviderRequest(successRefundRequest());
        assertTrue(response.isSuccessful());
        assertThat(response.getBaseResponse().get().getReference(), is(Optional.of("3014644340/1")));
    }

    @Test
    public void shouldNotRefundIfPaymentProviderReturnsErrorStatusCode() {
        mockPaymentProviderResponse(200, errorRefundResponse());
        GatewayResponse<BaseRefundResponse> response = provider.refund(buildTestRefundRequest());
        assertThat(response.isFailed(), is(true));
        assertThat(response.getGatewayError().isPresent(), is(true));
    }

    @Test
    public void shouldNotRefundIfPaymentProviderReturnsNon200HttpStatusCode() {
        mockPaymentProviderResponse(400, errorRefundResponse());
        GatewayResponse<BaseRefundResponse> response = provider.refund(buildTestRefundRequest());
        assertThat(response.isFailed(), is(true));
        assertThat(response.getGatewayError().isPresent(), is(true));
        assertEquals(response.getGatewayError().get(), new GatewayError("Unexpected HTTP status code 400 from gateway",
                UNEXPECTED_HTTP_STATUS_CODE_FROM_GATEWAY));
    }
}
