package com.me.bankingapp.account;

import com.me.bankingapp.account.model.Account;

import java.util.stream.Stream;

import static com.me.bankingapp.account.model.AccountType.*;

public class TestData {

    public static Stream<Account> getTestAccounts() {
        return Stream.of(
                getStudentAccount(),
                getZeroBalanceAccount(),
                getRegularSavingsAccount()
        );
    }

    public static Account getZeroBalanceAccount() {
        var account = new Account(ZERO_BALANCE, 0L);
        account.deposit(10000D);
        return account;
    }

    public static Account getStudentAccount() {
        var account = new Account(STUDENT, 1L);
        account.deposit(10000D);
        return account;
    }

    public static Account getRegularSavingsAccount() {
        var account = new Account(REGULAR_SAVINGS, 2L);
        account.deposit(10000D);
        return account;
    }

}
