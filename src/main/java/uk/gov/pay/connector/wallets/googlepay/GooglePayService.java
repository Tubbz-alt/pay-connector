package uk.gov.pay.connector.wallets.googlepay;

import uk.gov.pay.connector.paymentprocessor.service.AuthorisationService;
import uk.gov.pay.connector.wallets.WalletAuthorisationRequest;
import uk.gov.pay.connector.wallets.WalletService;
import uk.gov.pay.connector.wallets.WalletType;
import uk.gov.pay.connector.wallets.googlepay.api.GooglePayAuthRequest;
import uk.gov.pay.connector.wallets.model.WalletAuthorisationData;

import javax.inject.Inject;

public class GooglePayService extends WalletService {
    
    @Inject
    public GooglePayService(AuthorisationService authoriseService) {
        super(authoriseService, WalletType.GOOGLE_PAY);
    }
    
    @Override
    public WalletAuthorisationData getWalletAuthorisationData(String chargeId, WalletAuthorisationRequest googlePayAuthRequest) {
        return (GooglePayAuthRequest) googlePayAuthRequest;
    }
}
