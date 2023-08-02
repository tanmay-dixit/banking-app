package com.me.bankingapp.transaction.strategy.deposit;

import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.transaction.model.Transaction;

public interface DepositStrategy {

    Transaction execute(Account account, Double amount);

}
