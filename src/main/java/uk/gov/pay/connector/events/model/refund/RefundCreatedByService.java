package uk.gov.pay.connector.events.model.refund;

import uk.gov.pay.connector.events.eventdetails.refund.RefundCreatedByServiceEventDetails;
import uk.gov.pay.connector.refund.model.domain.RefundHistory;

import java.time.ZonedDateTime;

public class RefundCreatedByService extends RefundEvent {

    private RefundCreatedByService(String resourceExternalId, String parentResourceExternalId, RefundCreatedByServiceEventDetails eventDetails, ZonedDateTime timestamp) {
        super(resourceExternalId, parentResourceExternalId, eventDetails, timestamp);
    }

    public static RefundCreatedByService from(RefundHistory refundHistory, Long gatewayAccountId) {
        return new RefundCreatedByService(
                refundHistory.getExternalId(),
                refundHistory.getChargeExternalId(),
                new RefundCreatedByServiceEventDetails(
                        refundHistory.getAmount(),
                        gatewayAccountId.toString()),
                refundHistory.getHistoryStartDate()
        );

    }
}
