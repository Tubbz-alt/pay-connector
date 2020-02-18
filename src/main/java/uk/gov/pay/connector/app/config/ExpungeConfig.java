package uk.gov.pay.connector.app.config;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ExpungeConfig extends Configuration {

    @Valid
    @NotNull
    private int minimumAgeOfChargeInDays;

    @Valid
    @NotNull
    private int minimumAgeForHistoricChargeExceptions;

    @Valid
    @NotNull
    @Min(1)
    private int numberOfChargesToExpunge;

    @Valid
    @NotNull
    @Min(0)
    private int excludeChargesParityCheckedWithInDays;

    private boolean expungeChargesEnabled;

    public int getMinimumAgeOfChargeInDays() {
        return minimumAgeOfChargeInDays;
    }

    public int getNumberOfChargesToExpunge() {
        return numberOfChargesToExpunge;
    }

    public int getExcludeChargesParityCheckedWithInDays() {
        return excludeChargesParityCheckedWithInDays;
    }

    public boolean isExpungeChargesEnabled() {
        return expungeChargesEnabled;
    }

    public int getMinimumAgeForHistoricChargeExceptions() {
        return minimumAgeForHistoricChargeExceptions;
    }
}
