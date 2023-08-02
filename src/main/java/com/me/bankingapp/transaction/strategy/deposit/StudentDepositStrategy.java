package com.me.bankingapp.transaction.strategy.deposit;

import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.transaction.exception.DepositLimitReachedException;
import com.me.bankingapp.transaction.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class StudentDepositStrategy implements DepositStrategy {

    private static final Double MAX_DEPOSIT_LIMIT = 10_000.0;

    @Override
    public Transaction execute(Account studentAccount, Double amount) {
        checkDepositAmount(amount);
        return studentAccount.deposit(amount);
    }

    private void checkDepositAmount(Double amount) {
        if (amount > MAX_DEPOSIT_LIMIT) {
            throw new DepositLimitReachedException(MAX_DEPOSIT_LIMIT);
        }
    }


}
