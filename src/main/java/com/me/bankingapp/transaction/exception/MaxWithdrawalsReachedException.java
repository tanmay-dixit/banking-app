package com.me.bankingapp.transaction.exception;

import java.text.DecimalFormat;

public class MaxWithdrawalsReachedException extends RuntimeException {

    private static final DecimalFormat decimal = new DecimalFormat("#");

    public MaxWithdrawalsReachedException(Integer withdrawalLimit) {
        super(String.format("Account has already already reached withdrawal limit of %s transactions", decimal.format(withdrawalLimit)));
    }

}
