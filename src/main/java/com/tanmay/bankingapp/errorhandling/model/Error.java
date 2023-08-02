package com.tanmay.bankingapp.errorhandling.model;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.FieldError;

@Schema(description = "Provides detailed information about the error occurred")
public class Error {

    @Schema(description = "Describes the error in detail")
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
