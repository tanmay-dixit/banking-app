package com.me.bankingapp.transaction.model;

public enum TransactionType {

    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal");

    public final String value;

    TransactionType(String value) {
        this.value = value;
    }

}
