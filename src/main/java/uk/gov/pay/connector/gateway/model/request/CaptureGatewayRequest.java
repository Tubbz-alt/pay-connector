package uk.gov.pay.connector.gateway.model.request;

import uk.gov.pay.connector.charge.model.domain.ChargeEntity;
import uk.gov.pay.connector.gateway.GatewayOperation;
import uk.gov.pay.connector.gatewayaccount.model.GatewayAccountEntity;

public class CaptureGatewayRequest implements GatewayRequest {

    private ChargeEntity charge;

    private CaptureGatewayRequest(ChargeEntity charge) {
        this.charge = charge;
    }

    public String getAmount() {
        return String.valueOf(charge.getAmount());
    }

    public String getTransactionId() {
        return charge.getGatewayTransactionId();
    }

    @Override
    public GatewayAccountEntity getGatewayAccount() {
        return charge.getGatewayAccount();
    }

    @Override
    public GatewayOperation getRequestType() {
        return GatewayOperation.CAPTURE;
    }

    public static CaptureGatewayRequest valueOf(ChargeEntity charge) {
        return new CaptureGatewayRequest(charge);
    }
}