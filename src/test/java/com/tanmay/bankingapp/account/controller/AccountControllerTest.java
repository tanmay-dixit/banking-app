package com.tanmay.bankingapp.account.controller;

import com.tanmay.bankingapp.account.exception.AccountAlreadyExistsException;
import com.tanmay.bankingapp.account.exception.AccountNotFoundException;
import com.tanmay.bankingapp.account.exception.InvalidAccountTypeException;
import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.account.service.AccountService;
import com.tanmay.bankingapp.account.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    AccountService accountService;
    @Autowired
    private MockMvc mvc;

    private static Stream<Account> testAccounts() {
        return Stream.of(
                TestData.getStudentAccount(),
                TestData.getZeroBalanceAccount(),
                TestData.getRegularSavingsAccount()
        );
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void retrieveAccount_success(Account account) throws Exception {
        when(accountService.retrieveAccount(account.getNumber()))
                .thenReturn(account);

        mvc.perform(get("/accounts/" + account.getNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(account.getNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()))
                .andExpect(jsonPath("$.type").value(account.getType().toString()))
                .andExpect(jsonPath("$.kycEnabled").value(account.getKycEnabled()));
    }

    @Test
    void retrieveAccount_NotFound() throws Exception {
        var accountNumber = 234L;

        when(accountService.retrieveAccount(accountNumber))
                .thenThrow(new AccountNotFoundException(accountNumber));

        mvc.perform(get("/accounts/" + accountNumber))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].description").value("Account with number " + accountNumber + " does not exist"));
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void createAccount_Success(Account account) throws Exception {

        when(accountService.createAccount(account.getType().value, account.getNumber()))
                .thenReturn(account);

        mvc.perform(post("/accounts/" + account.getType().value)
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(account.getNumber())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value(account.getNumber()))
                .andExpect(jsonPath("$.balance").value(account.getBalance()))
                .andExpect(jsonPath("$.type").value(account.getType().toString()))
                .andExpect(jsonPath("$.kycEnabled").value(account.getKycEnabled()));
    }

    @Test
    void createAccount_InvalidAccountType() throws Exception {
        var accountNumber = 234L;
        var accountType = "invalid";

        when(accountService.createAccount(accountType, accountNumber))
                .thenThrow(new InvalidAccountTypeException(accountType));

        mvc.perform(post("/accounts/" + accountType)
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(accountNumber)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].description").value("Invalid account type: " + accountType));
    }

    @Test
    void createAccount_AccountAlreadyExists() throws Exception {
        var accountNumber = 234L;
        var accountType = "student";

        when(accountService.createAccount(accountType, accountNumber))
                .thenThrow(new AccountAlreadyExistsException(accountNumber));

        mvc.perform(post("/accounts/" + accountType)
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(accountNumber)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].description").value("Account with number " + accountNumber + " already exists"));
    }

    @Test
    void createAccount_NoRequestBody() throws Exception {
        var accountNumber = 234L;
        var accountType = "student";

        mvc.perform(post("/accounts/" + accountType))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].description").value("Required request body is missing"));
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void updateKyc_Success(Account account) throws Exception {
        var newKycStatus = true;

        doNothing().when(accountService).updateKyc(account.getNumber(), newKycStatus);

        mvc.perform(patch("/accounts/" + account.getNumber() + "/kyc")
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(newKycStatus)))
                .andExpect(status().isOk());
    }

    @Test
    void updateKyc_AccountNotFound() throws Exception {
        var accountNumber = 234L;
        var newKycStatus = false;

        doThrow(new AccountNotFoundException(accountNumber)).when(accountService).updateKyc(accountNumber, newKycStatus);

        mvc.perform(patch("/accounts/" + accountNumber + "/kyc")
                        .contentType(APPLICATION_JSON)
                        .content(String.valueOf(newKycStatus)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].description").value("Account with number " + accountNumber + " does not exist"));
    }

    @Test
    void updateKyc_NoRequestBody() throws Exception {
        var accountNumber = 234L;
        var newKycStatus = false;

        mvc.perform(patch("/accounts/" + accountNumber + "/kyc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].description").value("Required request body is missing"));
    }

}
