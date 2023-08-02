package com.tanmay.bankingapp.transaction.strategy.deposit;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.exception.KycDisabledException;
import com.tanmay.bankingapp.transaction.model.Transaction;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.FALSE;

@Component
public class DefaultDepositStrategy implements DepositStrategy {

    private static final Double LARGE_DEPOSIT_THRESHOLD = 50_000.0;

    @Override
    public Transaction execute(Account account, Double amount) {
        checkKycForLargeDeposits(account, amount);
        return account.deposit(amount);
    }

    private void checkKycForLargeDeposits(Account account, Double amount) {
        if (amount > LARGE_DEPOSIT_THRESHOLD && FALSE.equals(account.getKycEnabled())) {
            throw new KycDisabledException(LARGE_DEPOSIT_THRESHOLD);
        }
    }

}
