package com.tanmay.bankingapp.transaction.strategy.deposit;

import com.tanmay.bankingapp.account.model.AccountType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DepositStrategyConfiguration {

    private final StudentDepositStrategy studentDepositStrategy;
    private final DefaultDepositStrategy defaultDepositStrategy;


    public DepositStrategyConfiguration(StudentDepositStrategy studentDepositStrategy,
                                        DefaultDepositStrategy defaultDepositStrategy) {
        this.studentDepositStrategy = studentDepositStrategy;
        this.defaultDepositStrategy = defaultDepositStrategy;
    }

    @Bean
    public Map<AccountType, DepositStrategy> depositStrategies() {
        return Map.of(
                AccountType.STUDENT, studentDepositStrategy,
                AccountType.ZERO_BALANCE, defaultDepositStrategy,
                AccountType.REGULAR_SAVINGS, defaultDepositStrategy
        );
    }

}
