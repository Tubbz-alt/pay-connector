package uk.gov.pay.connector.paymentprocessor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.connector.charge.model.domain.ChargeEntity;
import uk.gov.pay.connector.gateway.GatewayException;
import uk.gov.pay.connector.gateway.PaymentProviders;
import uk.gov.pay.connector.gateway.ChargeQueryResponse;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

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

    public boolean isTerminableWithGateway(ChargeEntity charge) {
        try {
            return getChargeGatewayStatus(charge)
                    .getMappedStatus()
                    .map(chargeStatus -> !chargeStatus.toExternal().isFinished())
                    .orElse(false);
        } catch (UnsupportedOperationException e) {
            return true;
        } catch (WebApplicationException | GatewayException | IllegalArgumentException e) {
            logger.info("Unable to retrieve status for charge {}: {}", charge.getExternalId(), e.getMessage());
            return false;
        }
    }
}
