package com.tanmay.bankingapp.transaction.strategy.deposit;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.exception.DepositLimitReachedException;
import com.tanmay.bankingapp.transaction.model.Transaction;
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
