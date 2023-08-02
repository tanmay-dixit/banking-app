package com.tanmay.bankingapp.transaction.exception;

import java.text.DecimalFormat;

public class DepositLimitReachedException extends RuntimeException {

    private static final DecimalFormat decimal = new DecimalFormat("#");

    public DepositLimitReachedException(Double studentDepositLimit) {
        super(String.format("Student account does not support deposits larger than %s", decimal.format(studentDepositLimit)));
    }

}
