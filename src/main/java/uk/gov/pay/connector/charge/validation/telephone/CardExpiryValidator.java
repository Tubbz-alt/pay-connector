package uk.gov.pay.connector.charge.validation.telephone;

import uk.gov.pay.commons.model.CardExpiryDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CardExpiryValidator implements ConstraintValidator<ValidCardExpiryDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || CardExpiryDate.CARD_EXPIRY_DATE_PATTERN.matcher(value).matches();
    }

}
