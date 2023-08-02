package com.tanmay.bankingapp.account.model;

import com.tanmay.bankingapp.transaction.exception.InsufficientFundsException;
import com.tanmay.bankingapp.transaction.model.Transaction;
import com.tanmay.bankingapp.transaction.model.TransactionType;
import jakarta.persistence.*;

import java.util.Objects;

import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private Long number;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private Double balance;

    private Boolean kycEnabled;

    public Account(AccountType type, Long number) {
        this.number = number;
        this.type = type;
        this.balance = 0.0;
        this.kycEnabled = FALSE;
    }

    public Account() {
    }

    public Long getNumber() {
        return number;
    }

    public AccountType getType() {
        return type;
    }

    public Double getBalance() {
        return balance;
    }

    public Boolean getKycEnabled() {
        return kycEnabled;
    }

    public void setKycEnabled(Boolean kycEnabled) {
        this.kycEnabled = kycEnabled;
    }

    public synchronized Transaction withdraw(Double amount) {
        if (balance < amount) {
            throw new InsufficientFundsException(amount, 0.0);
        }
        balance -= amount;
        return new Transaction(this, amount, TransactionType.WITHDRAWAL);
    }

    public synchronized Transaction deposit(Double amount) {
        balance += amount;
        return new Transaction(this, amount, TransactionType.DEPOSIT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", type=" + type +
                ", balance=" + balance +
                ", kycEnabled=" + kycEnabled +
                '}';
    }
}
