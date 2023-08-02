package com.me.bankingapp.account.exception;

public class InvalidAccountTypeException extends RuntimeException {

    public InvalidAccountTypeException(String accountType) {
        super(String.format("Invalid account type: %s", accountType));
    }

}
