package com.tanmay.bankingapp.account.exception;

public class AccountAlreadyExistsException extends RuntimeException {

    public AccountAlreadyExistsException(Long accountNumber) {
        super(String.format("Account with number %d already exists", accountNumber));
    }

}
