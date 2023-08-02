package com.me.bankingapp.account.service;

import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.account.model.AccountType;
import org.springframework.stereotype.Service;

import static com.me.bankingapp.account.model.AccountType.*;

@Service
public class AccountFactory {

    public Account createNewAccount(AccountType type, Long accountNumber) {
        return switch (type) {
            case REGULAR_SAVINGS -> createRegularSavingsAccount(accountNumber);
            case STUDENT -> createStudentAccount(accountNumber);
            case ZERO_BALANCE -> createZeroBalanceAccount(accountNumber);
        };
    }

    private Account createZeroBalanceAccount(Long accountNumber) {
        return new Account(ZERO_BALANCE, accountNumber);
    }

    private Account createStudentAccount(Long accountNumber) {
        return new Account(STUDENT, accountNumber);
    }

    private Account createRegularSavingsAccount(Long accountNumber) {
        return new Account(REGULAR_SAVINGS, accountNumber);
    }

}
