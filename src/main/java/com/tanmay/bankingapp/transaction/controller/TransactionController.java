package com.tanmay.bankingapp.transaction.controller;

import com.tanmay.bankingapp.transaction.model.Transaction;
import com.tanmay.bankingapp.transaction.model.TransactionRequest;
import com.tanmay.bankingapp.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw amount from an Account", responses = {
            @ApiResponse(responseCode = "201", description = "Withdrawal successful", content = @Content(schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect payload"),
            @ApiResponse(responseCode = "403", description = "Withdrawal failed"),
            @ApiResponse(responseCode = "404", description = "Account does not exist")
    })
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionRequest request) {
        var transactionDetails = transactionService.processWithdrawalRequest(request);
        return ResponseEntity.status(CREATED).body(transactionDetails);
    }

    @PostMapping("/deposit")
    @Operation(summary = "Deposit amount to an account", responses = {
            @ApiResponse(responseCode = "201", description = "Deposit successful", content = @Content(schema = @Schema(implementation = Transaction.class))),
            @ApiResponse(responseCode = "400", description = "Incorrect payload"),
            @ApiResponse(responseCode = "403", description = "Deposit failed"),
            @ApiResponse(responseCode = "404", description = "Account does not exist")
    })
    public ResponseEntity<Transaction> createAccount(@RequestBody TransactionRequest request) {
        var transactionDetails = transactionService.processDepositRequest(request);
        return ResponseEntity.status(CREATED).body(transactionDetails);
    }

}
