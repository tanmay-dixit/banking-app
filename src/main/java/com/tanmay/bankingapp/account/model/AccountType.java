package com.tanmay.bankingapp.account.model;

import com.tanmay.bankingapp.account.exception.InvalidAccountTypeException;

public enum AccountType {


    REGULAR_SAVINGS("regular_savings"),
    STUDENT("student"),
    ZERO_BALANCE("zero_balance");

    public final String value;

    AccountType(String value) {
        this.value = value;
    }

    public static AccountType fromValue(String value) {
        for (AccountType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new InvalidAccountTypeException(value);
    }

}
