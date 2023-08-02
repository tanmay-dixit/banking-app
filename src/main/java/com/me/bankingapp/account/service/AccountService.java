package com.me.bankingapp.account.service;

import com.me.bankingapp.account.exception.AccountAlreadyExistsException;
import com.me.bankingapp.account.exception.AccountNotFoundException;
import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.account.model.AccountType;
import com.me.bankingapp.account.repository.AccountRepository;
import com.me.bankingapp.transaction.model.Transaction;
import com.me.bankingapp.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          AccountFactory accountFactory,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
        this.transactionRepository = transactionRepository;
    }

    public Account retrieveAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public Account createAccount(String accountTypeValue, Long accountNumber) {
        ensureAccountDoesNotExist(accountNumber);
        var accountType = AccountType.fromValue(accountTypeValue);
        var newAccount = accountFactory.createNewAccount(accountType, accountNumber);
        return accountRepository.save(newAccount);
    }


    public void updateKyc(Long accountNumber, Boolean newKycStatus) {
        var account = retrieveAccount(accountNumber);
        account.setKycEnabled(newKycStatus);
        accountRepository.save(account);
    }

    @Transactional
    public void saveAccountAndTransaction(Transaction transaction) {
        accountRepository.save(transaction.getAccount());
        transactionRepository.save(transaction);
    }

    private void ensureAccountDoesNotExist(Long accountNumber) {
        if (accountRepository.existsById(accountNumber)) {
            throw new AccountAlreadyExistsException(accountNumber);
        }
    }

}
