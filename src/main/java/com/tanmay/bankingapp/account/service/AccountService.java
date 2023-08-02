package com.tanmay.bankingapp.account.service;

import com.tanmay.bankingapp.account.exception.AccountAlreadyExistsException;
import com.tanmay.bankingapp.account.exception.AccountNotFoundException;
import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.account.model.AccountType;
import com.tanmay.bankingapp.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;

    public AccountService(AccountRepository accountRepository,
                          AccountFactory accountFactory) {
        this.accountRepository = accountRepository;
        this.accountFactory = accountFactory;
    }

    public Account retrieveAccount(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public Account createAccount(String accountTypeValue, Long accountNumber) {
        ensureAccountDoesNotExist(accountNumber);
        var accountType = AccountType.fromValue(accountTypeValue);
        var newAccount = accountFactory.createNewAccount(accountType, accountNumber);
        return saveAccount(newAccount);
    }


    public void updateKyc(Long accountNumber, Boolean newKycStatus) {
        var account = retrieveAccount(accountNumber);
        account.setKycEnabled(newKycStatus);
        saveAccount(account);
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    private void ensureAccountDoesNotExist(Long accountNumber) {
        if (accountRepository.existsById(accountNumber)) {
            throw new AccountAlreadyExistsException(accountNumber);
        }
    }

}
