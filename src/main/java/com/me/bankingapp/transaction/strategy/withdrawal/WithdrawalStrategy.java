package com.me.bankingapp.transaction.strategy.withdrawal;

import com.me.bankingapp.account.model.Account;
import com.me.bankingapp.transaction.model.Transaction;

public interface WithdrawalStrategy {

    Transaction execute(Account account, Double amount);

}
