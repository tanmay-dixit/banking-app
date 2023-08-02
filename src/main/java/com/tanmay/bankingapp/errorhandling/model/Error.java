package com.tanmay.bankingapp.errorhandling.model;

import org.springframework.validation.FieldError;

public class Error {

    private String description;

    public Error() {
    }

    public Error(FieldError fieldError) {
        this.description = fieldError.getDefaultMessage();
    }

    public Error(Exception e) {
        this.description = e.getMessage();
    }

    public Error(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
