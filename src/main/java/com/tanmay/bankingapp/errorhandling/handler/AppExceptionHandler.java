package com.tanmay.bankingapp.errorhandling.handler;

import com.tanmay.bankingapp.account.exception.AccountAlreadyExistsException;
import com.tanmay.bankingapp.account.exception.AccountNotFoundException;
import com.tanmay.bankingapp.account.exception.InvalidAccountTypeException;
import com.tanmay.bankingapp.errorhandling.model.Error;
import com.tanmay.bankingapp.errorhandling.model.FailureResponse;
import com.tanmay.bankingapp.transaction.exception.DepositLimitReachedException;
import com.tanmay.bankingapp.transaction.exception.InsufficientFundsException;
import com.tanmay.bankingapp.transaction.exception.KycDisabledException;
import com.tanmay.bankingapp.transaction.exception.MaxWithdrawalsReachedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public FailureResponse handleMethodArgumentException(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(Error::new)
                .toList();

        return new FailureResponse(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public FailureResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        var error = new Error("Required request body is missing");
        return new FailureResponse(error);
    }

    @ExceptionHandler(InvalidAccountTypeException.class)
    @ResponseStatus(BAD_REQUEST)
    public FailureResponse handleInvalidAccountTypeException(InvalidAccountTypeException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }

    @ExceptionHandler(DepositLimitReachedException.class)
    @ResponseStatus(FORBIDDEN)
    public FailureResponse handleDepositLimitReachedException(DepositLimitReachedException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }


    @ExceptionHandler(InsufficientFundsException.class)
    @ResponseStatus(FORBIDDEN)
    public FailureResponse handleInsufficientFundsException(InsufficientFundsException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }


    @ExceptionHandler(KycDisabledException.class)
    @ResponseStatus(FORBIDDEN)
    public FailureResponse handleKycDisabledException(KycDisabledException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }

    @ExceptionHandler(MaxWithdrawalsReachedException.class)
    @ResponseStatus(FORBIDDEN)
    public FailureResponse handleMaxWithdrawalsReachedException(MaxWithdrawalsReachedException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public FailureResponse handleAccountNotFoundException(AccountNotFoundException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public FailureResponse handleAccountAlreadyExistsException(AccountAlreadyExistsException exception) {
        var error = new Error(exception);
        return new FailureResponse(error);
    }

}
