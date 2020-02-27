package uk.gov.pay.connector.it.resources.worldpay;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.gov.pay.connector.app.ConnectorApp;
import uk.gov.pay.connector.charge.model.domain.ChargeStatus;
import uk.gov.pay.connector.it.base.ChargingITestBase;
import uk.gov.pay.connector.junit.DropwizardConfig;
import uk.gov.pay.connector.junit.DropwizardJUnitRunner;

@RunWith(DropwizardJUnitRunner.class)
@DropwizardConfig(app = ConnectorApp.class, config = "config/test-it-config.yaml")
public class WorldpayChargeCancelResourceIT extends ChargingITestBase {

    public WorldpayChargeCancelResourceIT() {
        super("worldpay");
    }

    @Test
    public void cancelCharge_inWorldpaySystem() {
        String chargeId = createNewCharge(ChargeStatus.AUTHORISATION_SUCCESS);

        worldpayMockClient.mockCancelSuccess();
        worldpayMockClient.mockAuthorisationQuerySuccess();
        givenSetup()
                .contentType(ContentType.JSON)
                .post(cancelChargeUrlFor(accountId, chargeId))
                .then()
                .statusCode(204);
    }
}
