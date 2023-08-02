package com.tanmay.bankingapp.transaction.strategy.deposit;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.model.Transaction;

public interface DepositStrategy {

    Transaction execute(Account account, Double amount);

}
