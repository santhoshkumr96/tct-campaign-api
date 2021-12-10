package com.tct.tctcampaign.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class QuestionError {
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResponse> validationFailed(Exception ex) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
