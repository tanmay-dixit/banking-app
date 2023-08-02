package com.me.bankingapp.transaction.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Payload for withdrawing amount from / depositing amount to an account")
public class TransactionRequest {

    @Schema(description = "Transaction amount", example = "20000")
    @NotNull(message = "Transaction is a required value")
    @Positive(message = "Transaction amount must be a positive value")
    private Double amount;

    @Schema(description = "Account Number", example = "123")
    @NotNull(message = "Account Number is a required number")
    @PositiveOrZero(message = "Account Number must be a non-negative number")
    private Long accountNumber;

    public TransactionRequest(Double amount,
                              Long accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;
    }

    public TransactionRequest() {
    }

    public Double getAmount() {
        return amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

}
