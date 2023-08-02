package com.tanmay.bankingapp.account.service;

import com.tanmay.bankingapp.account.model.Account;
import com.tanmay.bankingapp.account.TestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class AccountFactoryTest {

    @InjectMocks
    AccountFactory accountFactory;

    private static Stream<Account> testAccounts() {
        return Stream.of(
                TestData.getStudentAccount(),
                TestData.getZeroBalanceAccount(),
                TestData.getRegularSavingsAccount()
        );
    }

    @ParameterizedTest
    @MethodSource("testAccounts")
    void validAccountCreation(Account account) {
        var createdAccount = accountFactory.createNewAccount(account.getType(), account.getNumber());
        Assertions.assertThat(createdAccount).isEqualTo(account);
    }

    @Test
    void invalidAccountCreation() {
        var sampleAccountNumber = 123L;
        Assertions.assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> accountFactory.createNewAccount(null, sampleAccountNumber));
    }

}
