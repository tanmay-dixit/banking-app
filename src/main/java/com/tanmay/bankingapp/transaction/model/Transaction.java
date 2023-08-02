package com.tanmay.bankingapp.transaction.model;

import com.tanmay.bankingapp.account.model.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;

    private Double amount;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    public Transaction() {
    }

    public Transaction(Account account,
                       Double amount,
                       TransactionType type) {
        this.account = account;
        this.amount = amount;
        this.type = type;
        this.dateTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
