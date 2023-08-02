package com.tanmay.bankingapp.account.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long accountNumber) {
        super(String.format("Account with number %d does not exist", accountNumber));
    }

}
