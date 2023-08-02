package com.me.bankingapp.transaction.service;

import com.me.bankingapp.account.model.AccountType;
import com.me.bankingapp.account.service.AccountService;
import com.me.bankingapp.transaction.model.Transaction;
import com.me.bankingapp.transaction.model.TransactionRequest;
import com.me.bankingapp.transaction.strategy.deposit.DepositStrategy;
import com.me.bankingapp.transaction.strategy.withdrawal.WithdrawalStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionService {

    private final AccountService accountService;
    private final Map<AccountType, WithdrawalStrategy> withdrawalStrategies;
    private final Map<AccountType, DepositStrategy> depositStrategies;

    public TransactionService(AccountService accountService,
                              Map<AccountType, WithdrawalStrategy> withdrawalStrategies,
                              Map<AccountType, DepositStrategy> depositStrategies) {
        this.accountService = accountService;
        this.withdrawalStrategies = withdrawalStrategies;
        this.depositStrategies = depositStrategies;
    }

    public Transaction processWithdrawalRequest(TransactionRequest request) {
        var account = accountService.retrieveAccount(request.getAccountNumber());
        var withdrawalStrategy = withdrawalStrategies.get(account.getType());
        var transaction = withdrawalStrategy.execute(account, request.getAmount());
        accountService.saveAccountAndTransaction(transaction);
        return transaction;
    }

    public Transaction processDepositRequest(TransactionRequest request) {
        var account = accountService.retrieveAccount(request.getAccountNumber());
        var depositStrategy = depositStrategies.get(account.getType());
        var transaction = depositStrategy.execute(account, request.getAmount());
        accountService.saveAccountAndTransaction(transaction);
        return transaction;
    }

}
