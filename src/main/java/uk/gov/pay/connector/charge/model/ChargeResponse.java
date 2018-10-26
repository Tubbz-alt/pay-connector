package uk.gov.pay.connector.charge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import uk.gov.pay.commons.model.SupportedLanguage;
import uk.gov.pay.connector.charge.model.builder.AbstractChargeResponseBuilder;
import uk.gov.pay.connector.charge.model.domain.PersistedCard;
import uk.gov.pay.connector.common.model.api.ExternalTransactionState;
import uk.gov.pay.connector.util.DateTimeUtils;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ChargeResponse {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static class RefundSummary {

        @JsonProperty("status")
        private String status;
        @JsonProperty("user_external_id")
        private String userExternalId;
        @JsonProperty("amount_available")
        private Long amountAvailable;
        @JsonProperty("amount_submitted")
        private Long amountSubmitted;

        public void setStatus(String status) {
            this.status = status;
        }

        public void setAmountAvailable(Long amountAvailable) {
            this.amountAvailable = amountAvailable;
        }

        public void setAmountSubmitted(Long amountSubmitted) {
            this.amountSubmitted = amountSubmitted;
        }

        public String getUserExternalId() {
            return userExternalId;
        }

        public void setUserExternalId(String userExternalId) {
            this.userExternalId = userExternalId;
        }

        public Long getAmountAvailable() {
            return amountAvailable;
        }

        public Long getAmountSubmitted() {
            return amountSubmitted;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            RefundSummary that = (RefundSummary) o;

            if (!status.equals(that.status)) {
                return false;
            }
            if (userExternalId != null ? !userExternalId.equals(that.userExternalId)
                    : that.userExternalId != null) {
                return false;
            }
            if (amountAvailable != null ? !amountAvailable.equals(that.amountAvailable)
                    : that.amountAvailable != null) {
                return false;
            }
            return amountSubmitted != null ? amountSubmitted.equals(that.amountSubmitted)
                    : that.amountSubmitted == null;
        }

        @Override
        public int hashCode() {
            int result = status.hashCode();
            result = 31 * result + (userExternalId != null ? userExternalId.hashCode() : 0);
            result = 31 * result + (amountAvailable != null ? amountAvailable.hashCode() : 0);
            result = 31 * result + (amountSubmitted != null ? amountSubmitted.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "RefundSummary{" +
                    "status='" + status + '\'' +
                    "userExternalId='" + userExternalId + '\'' +
                    ", amountAvailable=" + amountAvailable +
                    ", amountSubmitted=" + amountSubmitted +
                    '}';
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static class SettlementSummary {
        private ZonedDateTime captureSubmitTime, capturedTime;

        public void setCaptureSubmitTime(ZonedDateTime captureSubmitTime) {
            this.captureSubmitTime = captureSubmitTime;
        }

        @JsonProperty("capture_submit_time")
        public String getCaptureSubmitTime() {
            return (captureSubmitTime != null) ? DateTimeUtils.toUTCDateTimeString(captureSubmitTime) : null;
        }

        public void setCapturedTime(ZonedDateTime capturedTime) {
            this.capturedTime = capturedTime;
        }

        @JsonProperty("captured_date")
        public String getCapturedDate() {
            return (capturedTime != null) ? DateTimeUtils.toUTCDateString(capturedTime) : null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SettlementSummary that = (SettlementSummary) o;

            if (captureSubmitTime != null ? !captureSubmitTime.equals(that.captureSubmitTime) : that.captureSubmitTime != null)
                return false;
            return capturedTime != null ? capturedTime.equals(that.capturedTime) : that.capturedTime == null;

        }

        @Override
        public int hashCode() {
            int result = captureSubmitTime != null ? captureSubmitTime.hashCode() : 0;
            result = 31 * result + (capturedTime != null ? capturedTime.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "SettlementSummary{" +
                    ", captureSubmitTime=" + captureSubmitTime +
                    ", capturedTime=" + capturedTime +
                    '}';
        }
    }

    @JsonInclude(Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public static class Auth3dsData {

        @JsonProperty("paRequest")
        private String paRequest;

        @JsonProperty("issuerUrl")
        private String issuerUrl;

        @JsonProperty("htmlOut")
        private String htmlOut;

        @JsonProperty("md")
        private String md;

        public String getPaRequest() {
            return paRequest;
        }

        public void setPaRequest(String paRequest) {
            this.paRequest = paRequest;
        }

        public String getIssuerUrl() {
            return issuerUrl;
        }

        public void setIssuerUrl(String issuerUrl) {
            this.issuerUrl = issuerUrl;
        }

        public String getHtmlOut() {
            return htmlOut;
        }

        public void setHtmlOut(String htmlOut) {
            this.htmlOut = htmlOut;
        }

        public void setMd(String md) {
            this.md = md;
        }

        public String getMd() {
            return md;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Auth3dsData that = (Auth3dsData) o;
            return Objects.equals(paRequest, that.paRequest) &&
                    Objects.equals(issuerUrl, that.issuerUrl) &&
                    Objects.equals(htmlOut, that.htmlOut) &&
                    Objects.equals(md, that.md);
        }

        @Override
        public int hashCode() {
            return Objects.hash(paRequest, issuerUrl, htmlOut, md);
        }

        @Override
        public String toString() {
            return "Auth3dsData{" +
                    "issuerUrl='" + issuerUrl + '\'' +
                    ", htmlOut='" + htmlOut + '\'' +
                    ", md='" + md + '\'' +
                    '}';
        }
    }

    public static class ChargeResponseBuilder extends AbstractChargeResponseBuilder<ChargeResponseBuilder, ChargeResponse> {
        @Override
        protected ChargeResponseBuilder thisObject() {
            return this;
        }

        @Override
        public ChargeResponse build() {
            return new ChargeResponse(chargeId, amount, state, cardBrand, gatewayTransactionId, returnUrl, email,
                    description, reference, providerName, createdDate, links, refundSummary, settlementSummary,
                    cardDetails, auth3dsData, language, delayedCapture, corporateCardSurcharge, totalAmount);
        }
    }

    public static ChargeResponseBuilder aChargeResponseBuilder() {
        return new ChargeResponseBuilder();
    }

    @JsonProperty("links")
    private List<Map<String, Object>> dataLinks = new ArrayList<>();

    @JsonProperty("charge_id")
    private String chargeId;

    @JsonProperty
    private Long amount;

    @JsonProperty
    private ExternalTransactionState state;

    @JsonProperty("card_brand")
    private String cardBrand;

    @JsonProperty("gateway_transaction_id")
    private String gatewayTransactionId;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("email")
    private String email;

    @JsonProperty
    private String description;

    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private ServicePaymentReference reference;

    @JsonProperty("payment_provider")
    private String providerName;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("refund_summary")
    private RefundSummary refundSummary;

    @JsonProperty("settlement_summary")
    private SettlementSummary settlementSummary;

    @JsonProperty("auth_3ds_data")
    private Auth3dsData auth3dsData;

    @JsonProperty("card_details")
    protected PersistedCard cardDetails;

    @JsonProperty
    @JsonSerialize(using = ToStringSerializer.class)
    private SupportedLanguage language;

    @JsonProperty("delayed_capture")
    private boolean delayedCapture;

    @JsonProperty("corporate_card_surcharge")
    private Long corporateCardSurcharge;

    @JsonProperty("total_amount")
    private Long totalAmount;


    protected ChargeResponse(String chargeId, Long amount, ExternalTransactionState state, String cardBrand, String gatewayTransactionId, String returnUrl,
                             String email, String description, ServicePaymentReference reference, String providerName, String createdDate,
                             List<Map<String, Object>> dataLinks, RefundSummary refundSummary, SettlementSummary settlementSummary, PersistedCard cardDetails,
                             Auth3dsData auth3dsData, SupportedLanguage language, boolean delayedCapture,
                             Long corporateCardSurcharge, Long totalAmount) {
        this.dataLinks = dataLinks;
        this.chargeId = chargeId;
        this.amount = amount;
        this.state = state;
        this.cardBrand = cardBrand;
        this.gatewayTransactionId = gatewayTransactionId;
        this.returnUrl = returnUrl;
        this.description = description;
        this.reference = reference;
        this.providerName = providerName;
        this.createdDate = createdDate;
        this.email = email;
        this.refundSummary = refundSummary;
        this.settlementSummary = settlementSummary;
        this.cardDetails = cardDetails;
        this.auth3dsData = auth3dsData;
        this.language = language;
        this.delayedCapture = delayedCapture;
        this.corporateCardSurcharge = corporateCardSurcharge;
        this.totalAmount = totalAmount;
    }

    public List<Map<String, Object>> getDataLinks() {
        return dataLinks;
    }

    public String getChargeId() {
        return chargeId;
    }

    public Long getAmount() {
        return amount;
    }

    public ExternalTransactionState getState() {
        return state;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public String getGatewayTransactionId() {
        return gatewayTransactionId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public ServicePaymentReference getReference() {
        return reference;
    }

    public String getProviderName() {
        return providerName;
    }

    public RefundSummary getRefundSummary() {
        return refundSummary;
    }

    public SettlementSummary getSettlementSummary() {
        return settlementSummary;
    }

    public Auth3dsData getAuth3dsData() {
        return auth3dsData;
    }

    public PersistedCard getCardDetails() {
        return cardDetails;
    }

    public SupportedLanguage getLanguage() {
        return language;
    }

    public boolean getDelayedCapture() {
        return delayedCapture;
    }

    public Long getCorporateCardSurcharge() {
        return corporateCardSurcharge;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public URI getLink(String rel) {
        return dataLinks.stream()
                .filter(map -> rel.equals(map.get("rel")))
                .findFirst()
                .map(link -> (URI) link.get("href"))
                .get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChargeResponse that = (ChargeResponse) o;
        return delayedCapture == that.delayedCapture &&
                Objects.equals(dataLinks, that.dataLinks) &&
                Objects.equals(chargeId, that.chargeId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(state, that.state) &&
                Objects.equals(cardBrand, that.cardBrand) &&
                Objects.equals(gatewayTransactionId, that.gatewayTransactionId) &&
                Objects.equals(returnUrl, that.returnUrl) &&
                Objects.equals(email, that.email) &&
                Objects.equals(description, that.description) &&
                Objects.equals(reference, that.reference) &&
                Objects.equals(providerName, that.providerName) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(refundSummary, that.refundSummary) &&
                Objects.equals(settlementSummary, that.settlementSummary) &&
                Objects.equals(auth3dsData, that.auth3dsData) &&
                Objects.equals(cardDetails, that.cardDetails) &&
                Objects.equals(corporateCardSurcharge, that.corporateCardSurcharge) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                language == that.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataLinks, chargeId, amount, state, cardBrand, gatewayTransactionId, returnUrl, email,
                description, reference, providerName, createdDate, refundSummary, settlementSummary, auth3dsData,
                cardDetails, language, delayedCapture, corporateCardSurcharge, totalAmount);
    }

    @Override
    public String toString() {
        // Some services put PII in the description, so don’t include it in the stringification
        return "ChargeResponse{" +
                "dataLinks=" + dataLinks +
                ", chargeId='" + chargeId + '\'' +
                ", amount=" + amount +
                ", state=" + state +
                ", cardBrand='" + cardBrand + '\'' +
                ", gatewayTransactionId='" + gatewayTransactionId + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", reference='" + reference + '\'' +
                ", providerName='" + providerName + '\'' +
                ", createdDate=" + createdDate +
                ", refundSummary=" + refundSummary +
                ", settlementSummary=" + settlementSummary +
                ", auth3dsData=" + auth3dsData +
                ", language=" + language +
                ", delayedCapture=" + delayedCapture +
                ", corporateCardSurcharge=" + corporateCardSurcharge +
                ", totalAmount=" + totalAmount +
                '}';
    }

}

