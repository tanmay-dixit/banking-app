package com.tanmay.bankingapp.transaction.strategy.withdrawal;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.transaction.model.Transaction;

public interface WithdrawalStrategy {

    Transaction execute(Account account, Double amount);

}
