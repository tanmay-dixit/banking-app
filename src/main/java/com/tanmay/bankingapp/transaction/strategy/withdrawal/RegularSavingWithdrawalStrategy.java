package com.tanmay.bankingapp.transaction.strategy.withdrawal;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.exception.MaxWithdrawalsReachedException;
import com.tanmay.bankingapp.transaction.model.Transaction;
import com.tanmay.bankingapp.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class RegularSavingWithdrawalStrategy implements WithdrawalStrategy {

    private static final Integer MAX_MONTHLY_WITHDRAWALS = 4;

    private final TransactionRepository transactionRepository;

    public RegularSavingWithdrawalStrategy(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction execute(Account regularSavingsAccount, Double amount) {
        checkMonthlyWithdrawalLimit(regularSavingsAccount.getNumber());
        return regularSavingsAccount.withdraw(amount);
    }

    private void checkMonthlyWithdrawalLimit(Long accountNumber) {
        var withdrawalsInCurrentMonth = transactionRepository.countWithdrawalsInCurrentMonth(accountNumber);
        if (withdrawalsInCurrentMonth > MAX_MONTHLY_WITHDRAWALS) {
            throw new MaxWithdrawalsReachedException(MAX_MONTHLY_WITHDRAWALS);
        }
    }

}
