package com.me.bankingapp.transaction.strategy.withdrawal;

import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.transaction.exception.InsufficientFundsException;
import com.me.bankingapp.transaction.exception.MaxWithdrawalsReachedException;
import com.me.bankingapp.transaction.model.Transaction;
import com.me.bankingapp.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class StudentWithdrawalStrategy implements WithdrawalStrategy {

    private static final Double MINIMUM_BALANCE = 1_000.0;
    private static final Integer MAX_MONTHLY_WITHDRAWALS = 4;

    private final TransactionRepository transactionRepository;

    public StudentWithdrawalStrategy(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction execute(Account studentAccount, Double amount) {
        checkBalanceExceedsThreshold(studentAccount.getBalance() - amount);
        checkMonthlyWithdrawalLimit(studentAccount.getNumber());
        return studentAccount.withdraw(amount);
    }

    private void checkBalanceExceedsThreshold(Double newBalance) {
        if (newBalance < MINIMUM_BALANCE) {
            throw new InsufficientFundsException(newBalance, MINIMUM_BALANCE);
        }
    }

    private void checkMonthlyWithdrawalLimit(Long accountNumber) {
        var withdrawalsInCurrentMonth = transactionRepository.countWithdrawalsInCurrentMonth(accountNumber);
        if (withdrawalsInCurrentMonth >= MAX_MONTHLY_WITHDRAWALS) {
            throw new MaxWithdrawalsReachedException(MAX_MONTHLY_WITHDRAWALS);
        }
    }

}
