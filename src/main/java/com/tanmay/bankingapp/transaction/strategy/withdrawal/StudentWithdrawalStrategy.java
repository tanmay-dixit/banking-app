package com.tanmay.bankingapp.transaction.strategy.withdrawal;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.exception.InsufficientFundsException;
import com.tanmay.bankingapp.transaction.exception.MaxWithdrawalsReachedException;
import com.tanmay.bankingapp.transaction.model.Transaction;
import com.tanmay.bankingapp.transaction.repository.TransactionRepository;
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
