package com.me.bankingapp.transaction.strategy.withdrawal;

import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.transaction.exception.MaxWithdrawalsReachedException;
import com.me.bankingapp.transaction.model.Transaction;
import com.me.bankingapp.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class ZeroBalanceWithdrawalStrategy implements WithdrawalStrategy {

    private static final Integer MAX_MONTHLY_WITHDRAWALS = 4;

    private final TransactionRepository transactionRepository;

    public ZeroBalanceWithdrawalStrategy(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction execute(Account zeroBalanceaccount, Double amount) {
        checkMonthlyWithdrawalLimit(zeroBalanceaccount.getNumber());
        return zeroBalanceaccount.withdraw(amount);
    }

    private void checkMonthlyWithdrawalLimit(Long accountNumber) {
        var withdrawalsInCurrentMonth = transactionRepository.countWithdrawalsInCurrentMonth(accountNumber);
        if (withdrawalsInCurrentMonth >= MAX_MONTHLY_WITHDRAWALS) {
            throw new MaxWithdrawalsReachedException(MAX_MONTHLY_WITHDRAWALS);
        }
    }

}
