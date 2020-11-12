package uk.gov.pay.connector.wallets.applepay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.pay.connector.paymentprocessor.service.AuthorisationService;
import uk.gov.pay.connector.wallets.WalletAuthorisationRequest;
import uk.gov.pay.connector.wallets.WalletService;
import uk.gov.pay.connector.wallets.WalletType;
import uk.gov.pay.connector.wallets.applepay.api.ApplePayAuthRequest;
import uk.gov.pay.connector.wallets.model.WalletAuthorisationData;

import javax.inject.Inject;

public class ApplePayService extends WalletService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplePayService.class);

    private ApplePayDecrypter applePayDecrypter;

    @Inject
    public ApplePayService(ApplePayDecrypter applePayDecrypter, AuthorisationService authoriseService) {
        super(authoriseService, WalletType.APPLE_PAY);
        this.applePayDecrypter = applePayDecrypter;
    }
    
    @Override
    public WalletAuthorisationData getWalletAuthorisationData(String chargeId, WalletAuthorisationRequest applePayAuthRequest) {
        LOGGER.info("Decrypting apple pay payload for charge with id {}", chargeId);
        AppleDecryptedPaymentData result = applePayDecrypter.performDecryptOperation((ApplePayAuthRequest) applePayAuthRequest);
        result.setPaymentInfo(applePayAuthRequest.getPaymentInfo());
        LOGGER.info("Finished decryption for id {}", chargeId);
        return result;
    }
}
