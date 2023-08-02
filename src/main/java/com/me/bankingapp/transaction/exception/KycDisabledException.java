package com.me.bankingapp.transaction.exception;

import java.text.DecimalFormat;

public class KycDisabledException extends RuntimeException {

    private static final DecimalFormat decimal = new DecimalFormat("#");

    public KycDisabledException(Double largeTransactionThreshold) {
        super(String.format("Transactions larger than %s require KYC to be enabled", decimal.format(largeTransactionThreshold)));
    }

}
