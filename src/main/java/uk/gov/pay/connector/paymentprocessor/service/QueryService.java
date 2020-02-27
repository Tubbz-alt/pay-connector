package uk.gov.pay.connector.paymentprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.connector.charge.model.domain.ChargeEntity;
import uk.gov.pay.connector.charge.model.domain.ChargeStatus;
import uk.gov.pay.connector.gateway.GatewayException;
import uk.gov.pay.connector.gateway.PaymentGatewayName;
import uk.gov.pay.connector.gateway.PaymentProviders;
import uk.gov.pay.connector.gateway.ChargeQueryResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Optional;

public class QueryService {
    private final PaymentProviders providers;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public QueryService(PaymentProviders providers) {
        this.providers = providers;
    }
    
    public ChargeQueryResponse getChargeGatewayStatus(ChargeEntity charge) throws GatewayException {
        return providers.byName(charge.getPaymentGatewayName()).queryPaymentStatus(charge);
    }
    
    public boolean canQueryChargeGatewayStatus(PaymentGatewayName paymentGatewayName) {
        return providers.byName(paymentGatewayName).canQueryPaymentStatus();
    }

    public boolean isTerminableWithGateway(ChargeEntity charge) {
        try {
            return getChargeGatewayStatus(charge)
                    .getMappedStatus()
                    .map(chargeStatus -> !chargeStatus.toExternal().isFinished())
                    .orElse(false);
        } catch (WebApplicationException | UnsupportedOperationException | GatewayException | IllegalArgumentException e) {
            logger.info("Unable to retrieve status for charge {}: {}", charge.getExternalId(), e.getMessage());
            return false;
        }
    }
    
    public Optional<ChargeStatus> getMappedGatewayStatus(ChargeEntity charge) {
        try {
            return getChargeGatewayStatus(charge).getMappedStatus();
        } catch (WebApplicationException | UnsupportedOperationException | GatewayException | IllegalArgumentException e) {
            logger.info("Unable to retrieve status for charge {}: {}", charge.getExternalId(), e.getMessage());
            return Optional.empty();
        }
    }
}
