package com.islomar.payments.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.islomar.payments.core.model.exceptions.InvalidFieldError;
import com.islomar.payments.core.model.exceptions.InvalidPaymentException;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
public class PaymentRestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PaymentNotFoundException.class})
    void handleNotFoundError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        this.logError(request, ex);
        response.sendError(NOT_FOUND.value());
    }

    @ExceptionHandler({InvalidPaymentException.class})
    ResponseEntity<Object> handleInvalidPaymentError(HttpServletRequest request, InvalidPaymentException ex) {
        logger.error(String.format("%s request to %s had invalid fields: %s", request.getMethod(), request.getRequestURL(), ex.getErrors()));
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageConversionException.class, InvalidFormatException.class})
    void handleBadRequestError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        this.logError(request, ex);
        response.sendError(BAD_REQUEST.value());
    }

    @ExceptionHandler({Exception.class})
    @Order(Ordered.LOWEST_PRECEDENCE)
    void handleInternalServerError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        this.logError(request, ex);
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<InvalidFieldError> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(this::formatErrorMessage)
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            logger.error(String.format("Request %s with had invalid fields: %s", request.getDescription(false), errors.toString()));
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private InvalidFieldError formatErrorMessage(FieldError fieldError) {
        return new InvalidFieldError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    private void logError(HttpServletRequest request, Exception ex) {
        logger.error(String.format("%s request to %s raised %s", request.getMethod(), request.getRequestURL(), ex));
    }
}
