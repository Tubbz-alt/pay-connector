package uk.gov.pay.connector.events.eventdetails.payout;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uk.gov.pay.connector.events.MicrosecondPrecisionDateTimeSerializer;
import uk.gov.pay.connector.events.eventdetails.EventDetails;

import java.time.ZonedDateTime;

public class PayoutCreatedEventDetails extends EventDetails {

    private Long amount;
    @JsonSerialize(using = MicrosecondPrecisionDateTimeSerializer.class)
    private ZonedDateTime estimatedArrivalDateInBank;
    private String gatewayStatus;
    private String destinationType;
    private String statementDescriptor;

    public PayoutCreatedEventDetails(Long amount, ZonedDateTime estimatedArrivalDateInBank, String gatewayStatus, String destinationType, String statementDescriptor) {
        this.amount = amount;
        this.estimatedArrivalDateInBank = estimatedArrivalDateInBank;
        this.gatewayStatus = gatewayStatus;
        this.destinationType = destinationType;
        this.statementDescriptor = statementDescriptor;
    }

    public Long getAmount() {
        return amount;
    }

    public ZonedDateTime getEstimatedArrivalDateInBank() {
        return estimatedArrivalDateInBank;
    }

    public String getGatewayStatus() {
        return gatewayStatus;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }
}