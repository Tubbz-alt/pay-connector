package uk.gov.pay.connector.events.model.refund;

import uk.gov.pay.connector.events.eventdetails.refund.RefundEventWithGatewayTransactionIdDetails;
import uk.gov.pay.connector.refund.model.domain.RefundHistory;

import java.time.ZonedDateTime;

public class RefundSucceeded extends RefundEvent {

    public RefundSucceeded(String resourceExternalId, String parentResourceExternalId,
                           RefundEventWithGatewayTransactionIdDetails referenceDetails, ZonedDateTime timestamp) {
        super(resourceExternalId, parentResourceExternalId, referenceDetails, timestamp);
    }

    public static RefundSucceeded from(RefundHistory refundHistory) {
        return new RefundSucceeded(refundHistory.getExternalId(),
                refundHistory.getChargeExternalId(),
                new RefundEventWithGatewayTransactionIdDetails(refundHistory.getGatewayTransactionId()),
                refundHistory.getHistoryStartDate());
    }
}
