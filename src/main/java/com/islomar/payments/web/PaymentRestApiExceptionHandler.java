package com.islomar.payments.web;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

// https://blog.jayway.com/2013/02/03/improve-your-spring-rest-api-part-iii/

@ControllerAdvice
public class PaymentRestApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRestApiExceptionHandler.class);

    @ExceptionHandler({PaymentNotFoundException.class})
    void handleNotFoundError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(NOT_FOUND.value());
    }

    @ExceptionHandler({InvalidPaymentException.class})
    void handleInvalidPaymentError(HttpServletRequest request, HttpServletResponse response, InvalidPaymentException ex) throws IOException {
        System.out.println(">>>>>>>>>>>>>>>>>> 1");
        System.out.println(ex.getErrors());
        LOGGER.error(ex.getErrors().toString());
        response.sendError(BAD_REQUEST.value());
    }

    @ExceptionHandler({HttpMessageConversionException.class, InvalidFormatException.class})
    void handleBadRequestError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(BAD_REQUEST.value());
    }

    @ExceptionHandler({Exception.class})
    @Order(Ordered.LOWEST_PRECEDENCE)
    void handleInternalServerError(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        LOGGER.error("{} request: {} raised {}", request.getMethod(), request.getRequestURL(), ex);
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
//                ex.getBindingResult().toString());
        //FIXME
        System.out.println(">>>>>>>>>>>>>>>>>> 2");
        LOGGER.error(ex.getBindingResult().toString());
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
