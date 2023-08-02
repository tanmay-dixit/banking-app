package com.tanmay.bankingapp.transaction.strategy.withdrawal;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.exception.MaxWithdrawalsReachedException;
import com.tanmay.bankingapp.transaction.model.Transaction;
import com.tanmay.bankingapp.transaction.repository.TransactionRepository;
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
