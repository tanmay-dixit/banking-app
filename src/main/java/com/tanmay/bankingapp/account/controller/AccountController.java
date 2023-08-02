package com.tanmay.bankingapp.account.controller;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}")
    @Operation(
            summary = "Retrieve an existing account", responses = {
            @ApiResponse(responseCode = "200", description = "Account retrieved successfully", content = @Content(schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "404", description = "Account does not exist")
    })
    public ResponseEntity<Account> getAccount(@PathVariable @Parameter(description = "Account number") Long accountNumber) {
        var account = accountService.retrieveAccount(accountNumber);
        return ResponseEntity.status(OK).body(account);
    }


    @PostMapping("/{accountType}")
    @Operation(summary = "Create a new account", responses = {
            @ApiResponse(responseCode = "201", description = "Account created successfully", content = @Content(schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "400", description = "Invalid account type"),
            @ApiResponse(responseCode = "409", description = "Account already exists")
    })
    public ResponseEntity<Account> createAccount(@PathVariable @Parameter(description = "Type of account", required = true, examples = @ExampleObject("regular_savings")) String accountType,
                                                 @NotNull @RequestBody Long accountNumber) {
        var newAccount = accountService.createAccount(accountType, accountNumber);
        return ResponseEntity.status(CREATED).body(newAccount);
    }

    @Operation(summary = "Update KYC status for an existing account", responses = {
            @ApiResponse(responseCode = "200", description = "KYC status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Account does not exist")
    })
    @PatchMapping("/{accountNumber}/kyc")
    public ResponseEntity<Void> updateKycStatus(@PathVariable Long accountNumber,
                                                @NotNull @RequestBody Boolean newKycStatus) {
        accountService.updateKyc(accountNumber, newKycStatus);
        return ResponseEntity.status(OK).build();
    }

}
