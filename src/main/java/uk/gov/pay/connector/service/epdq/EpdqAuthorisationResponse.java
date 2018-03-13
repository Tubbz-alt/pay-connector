package uk.gov.pay.connector.service.epdq;

import org.apache.commons.lang3.StringUtils;
import uk.gov.pay.connector.model.Auth3dsDetailsDTO;
import uk.gov.pay.connector.service.BaseAuthoriseResponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;
import java.util.StringJoiner;

@XmlRootElement(name = "ncresponse")
public class EpdqAuthorisationResponse extends EpdqBaseResponse implements BaseAuthoriseResponse {

    private static final String AUTHORISED = "5";
    private static final String WAITING_3DS = "46";
    private static final String WAITING_EXTERNAL = "50";
    private static final String WAITING = "51";
    private static final String REJECTED = "2";

    @XmlAttribute(name = "STATUS")
    private String status;

    @XmlAttribute(name = "PAYID")
    private String transactionId;

    @XmlElement(name = "HTML_ANSWER")
    private String htmlAnswer;

    @Override
    public AuthoriseStatus authoriseStatus() {
        if (AUTHORISED.equals(status)) {
            return AuthoriseStatus.AUTHORISED;
        }

        if (WAITING_EXTERNAL.equals(status) ||
                WAITING.equals(status)) {
            return AuthoriseStatus.SUBMITTED;
        }

        if (REJECTED.equals(status)) {
            return AuthoriseStatus.REJECTED;
        }

        if(WAITING_3DS.equals(status)) {
            return AuthoriseStatus.REQUIRES_3DS;
        }
        return AuthoriseStatus.ERROR;
    }

    @Override
    public Optional<Auth3dsDetailsDTO> getAuth3dsDetails() {
        if (htmlAnswer != null) {
            return Optional.of(new Auth3dsDetailsDTO(null, null, htmlAnswer));
        }
        return Optional.empty();
    }

    private boolean hasError() {
        return authoriseStatus() == AuthoriseStatus.ERROR;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    public String getHtmlAnswer() {
        return htmlAnswer;
    }

    public void setHtmlAnswer(String htmlAnswer) {
        this.htmlAnswer = htmlAnswer;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getErrorCode() {
        if (hasError())
            return super.getErrorCode();
        return null;
    }

    @Override
    public String getErrorMessage() {
        if (hasError())
            return super.getErrorMessage();
        return null;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "ePDQ authorisation response (", ")");
        if (StringUtils.isNotBlank(getTransactionId())) {
            joiner.add("PAYID: " + getTransactionId());
        }
        if (StringUtils.isNotBlank(status)) {
            joiner.add("STATUS: " + status);
        }
        if (StringUtils.isNotBlank(getErrorCode())) {
            joiner.add("NCERROR: " + getErrorCode());
        }
        if (StringUtils.isNotBlank(getErrorMessage())) {
            joiner.add("NCERRORPLUS: " + getErrorMessage());
        }
        return joiner.toString();
    }
}
