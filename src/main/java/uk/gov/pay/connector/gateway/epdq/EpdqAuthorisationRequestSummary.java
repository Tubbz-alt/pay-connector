package uk.gov.pay.connector.gateway.epdq;

import uk.gov.pay.connector.charge.model.domain.ChargeEntity;
import uk.gov.pay.connector.gateway.model.AuthCardDetails;
import uk.gov.pay.connector.gateway.model.AuthorisationRequestSummary;

import static uk.gov.pay.connector.gateway.model.AuthorisationRequestSummary.Presence.NOT_PRESENT;
import static uk.gov.pay.connector.gateway.model.AuthorisationRequestSummary.Presence.PRESENT;

public class EpdqAuthorisationRequestSummary implements AuthorisationRequestSummary {

    private final Presence billingAddress;
    private final Presence dataFor3ds;
    private final Presence dataFor3ds2;

    public EpdqAuthorisationRequestSummary(ChargeEntity chargeEntity, AuthCardDetails authCardDetails) {
        billingAddress = authCardDetails.getAddress().map(address -> PRESENT).orElse(NOT_PRESENT);
        dataFor3ds = chargeEntity.getGatewayAccount().isRequires3ds() ? PRESENT : NOT_PRESENT;
        dataFor3ds2 = (chargeEntity.getGatewayAccount().isRequires3ds()
                && chargeEntity.getGatewayAccount().getIntegrationVersion3ds() == 2) ? PRESENT : NOT_PRESENT;
    }

    @Override
    public Presence billingAddress() {
        return billingAddress;
    }

    @Override
    public Presence dataFor3ds() {
        return dataFor3ds;
    }

    @Override
    public Presence dataFor3ds2() {
        return dataFor3ds2;
    }

}
