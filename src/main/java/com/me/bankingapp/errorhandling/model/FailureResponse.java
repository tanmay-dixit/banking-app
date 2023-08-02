package com.me.bankingapp.errorhandling.model;

import java.util.List;

public class FailureResponse {

    private List<Error> errors;

    public FailureResponse() {
    }

    public FailureResponse(List<Error> errors) {
        this.errors = List.copyOf(errors);
    }

    public FailureResponse(Error error) {
        this.errors = List.of(error);
    }

    public List<Error> getErrors() {
        return errors;
    }

}
