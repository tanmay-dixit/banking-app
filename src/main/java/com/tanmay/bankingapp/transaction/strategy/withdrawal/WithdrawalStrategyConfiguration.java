package com.tanmay.bankingapp.transaction.strategy.withdrawal;

import com.tanmay.bankingapp.account.model.AccountType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class WithdrawalStrategyConfiguration {

    private final StudentWithdrawalStrategy studentWithdrawalStrategy;
    private final ZeroBalanceWithdrawalStrategy zeroBalanceWithdrawalStrategy;
    private final RegularSavingWithdrawalStrategy regularSavingWithdrawalStrategy;


    public WithdrawalStrategyConfiguration(StudentWithdrawalStrategy studentWithdrawalStrategy,
                                           ZeroBalanceWithdrawalStrategy zeroBalanceWithdrawalStrategy,
                                           RegularSavingWithdrawalStrategy regularSavingWithdrawalStrategy) {
        this.studentWithdrawalStrategy = studentWithdrawalStrategy;
        this.zeroBalanceWithdrawalStrategy = zeroBalanceWithdrawalStrategy;
        this.regularSavingWithdrawalStrategy = regularSavingWithdrawalStrategy;
    }

    @Bean
    public Map<AccountType, WithdrawalStrategy> withdrawalStrategies() {
        return Map.of(
                AccountType.STUDENT, studentWithdrawalStrategy,
                AccountType.ZERO_BALANCE, zeroBalanceWithdrawalStrategy,
                AccountType.REGULAR_SAVINGS, regularSavingWithdrawalStrategy
        );
    }

}
