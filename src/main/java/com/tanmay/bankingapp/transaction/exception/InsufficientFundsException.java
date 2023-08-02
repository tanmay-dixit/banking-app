package com.tanmay.bankingapp.transaction.exception;

import java.text.DecimalFormat;

public class InsufficientFundsException extends RuntimeException {

    private static final DecimalFormat decimal = new DecimalFormat("#");

    public InsufficientFundsException(Double currentBalance, Double minimumBalance) {
        super(String.format("Account balance %s does not meet the minimum required balance of %s",
                decimal.format(currentBalance), decimal.format(minimumBalance)));
    }

}
