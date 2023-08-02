package com.tanmay.bankingapp.account.service;

import com.tanmay.bankingapp.account.exception.AccountAlreadyExistsException;
import com.tanmay.bankingapp.account.exception.AccountNotFoundException;
import com.tanmay.bankingapp.account.exception.InvalidAccountTypeException;
import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.account.model.AccountType;
import com.tanmay.bankingapp.account.repository.AccountRepository;
import com.tanmay.bankingapp.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static com.tanmay.bankingapp.account.TestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountFactory accountFactory;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private static Stream<Account> testAccounts() {
        return Stream.of(
                getStudentAccount(),
                getZeroBalanceAccount(),
                getRegularSavingsAccount()
        );
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void retrieveAccount_success(Account account) {
        when(accountRepository.findById(account.getNumber())).thenReturn(Optional.of(account));
        var retrievedAccount = accountService.retrieveAccount(account.getNumber());
        assertEquals(account, retrievedAccount);
        verify(accountRepository, times(1)).findById(account.getNumber());
    }

    @Test
    void retrieveAccount_AccountNotFound() {
        var invalidAccountNumber = 234L;

        when(accountRepository.findById(invalidAccountNumber)).thenReturn(Optional.empty());

        assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> accountService.retrieveAccount(invalidAccountNumber));

        verify(accountRepository, times(1)).findById(invalidAccountNumber);
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void createAccount_success(Account account) {
        when(accountRepository.existsById(account.getNumber())).thenReturn(false);
        when(accountFactory.createNewAccount(account.getType(), account.getNumber())).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);

        var createdAccount = accountService.createAccount(account.getType().value, account.getNumber());
        assertEquals(account, createdAccount);

        verify(accountRepository, times(1)).existsById(account.getNumber());
        verify(accountFactory, times(1)).createNewAccount(account.getType(), account.getNumber());
        verify(accountRepository, times(1)).save(account);
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void createAccount_AccountAlreadyExists(Account account) {
        when(accountRepository.existsById(account.getNumber())).thenReturn(true);

        assertThatExceptionOfType(AccountAlreadyExistsException.class)
                .isThrownBy(() -> accountService.createAccount(account.getType().value, account.getNumber()));

        verify(accountRepository, times(1)).existsById(account.getNumber());
        verify(accountFactory, times(0)).createNewAccount(any(AccountType.class), anyLong());
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    void createAccount_InvalidAccountType() {
        var accountNumber = 123L;
        var invalidAccountType = "invalid";
        when(accountRepository.existsById(accountNumber)).thenReturn(false);

        assertThatExceptionOfType(InvalidAccountTypeException.class)
                .isThrownBy(() -> accountService.createAccount(invalidAccountType, accountNumber));

        verify(accountRepository, times(1)).existsById(accountNumber);
        verify(accountFactory, times(0)).createNewAccount(any(AccountType.class), anyLong());
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void updateKyc_Success(Account account) {
        when(accountRepository.findById(account.getNumber())).thenReturn(Optional.of(account));

        var newKycStatus = true;
        accountService.updateKyc(account.getNumber(), newKycStatus);

        assertThat(account.getKycEnabled()).isTrue();
        verify(accountRepository, times(1)).findById(account.getNumber());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void updateKyc_AccountNotFound() {
        var invalidAccountNumber = 123L;
        var newKycStatus = true;

        when(accountRepository.findById(invalidAccountNumber)).thenReturn(Optional.empty());

        assertThatExceptionOfType(AccountNotFoundException.class).isThrownBy(() -> accountService.updateKyc(invalidAccountNumber, newKycStatus));

        verify(accountRepository, times(1)).findById(invalidAccountNumber);
        verify(accountRepository, times(0)).save(any(Account.class));
    }


}
