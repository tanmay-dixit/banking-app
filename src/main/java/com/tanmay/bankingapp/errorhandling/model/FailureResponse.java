package com.tanmay.bankingapp.errorhandling.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Provides a list of errors occurred during processing a request")
public class FailureResponse {

    @Schema(description = "List of error details")
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
