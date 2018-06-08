package com.islomar.payments.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.islomar.payments.core.model.exceptions.InvalidFieldError;
import com.islomar.payments.core.model.exceptions.InvalidPaymentException;
import com.islomar.payments.core.model.exceptions.PaymentNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

// https://blog.jayway.com/2013/02/03/improve-your-spring-rest-api-part-iii/

@ControllerAdvice
public class PaymentRestApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRestApiExceptionHandler.class);

    @ExceptionHandler({PaymentNotFoundException.class})
    void handleNotFoundError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(NOT_FOUND.value());
    }

    @ExceptionHandler({InvalidPaymentException.class})
    ResponseEntity<Object> handleInvalidPaymentError(InvalidPaymentException ex) throws IOException {
        System.out.println(">>>>>>>>>>>>>>>>>> 1");
        System.out.println(ex.toString());
        System.out.println(ex.getErrors());
        //LOGGER.error(ex.getErrors().toString());
        //response.sendError(BAD_REQUEST.value());
        //response.setStatus(BAD_REQUEST.value());
        System.out.println(">>>>>>>>>>>>>>>>>> 1.5");
        //return ex.getErrors();
        Object myResponse = ex.getErrors().get(0);
        System.out.println(">>>>>>>>>>>>>>>>>> 1.6");
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
        //response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler({HttpMessageConversionException.class, InvalidFormatException.class})
    void handleBadRequestError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value());
    }

    @ExceptionHandler({Exception.class})
    @Order(Ordered.LOWEST_PRECEDENCE)
    void handleInternalServerError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        LOGGER.error(">>>>> WTF!!!!");
        LOGGER.error("{} request: {} raised {}", request.getMethod(), request.getRequestURL(), ex);
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> handleMethodArgumentNotValid");
        List<InvalidFieldError> errors = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(this::formatErrorMessage)
                .collect(Collectors.toList());
        LOGGER.error(errors.toString());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    private InvalidFieldError formatErrorMessage(FieldError fieldError) {
        return new InvalidFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        //return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
    }
}
