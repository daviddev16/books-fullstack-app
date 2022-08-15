package org.books.handler;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.books.view.ValidationErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers,
                                                          HttpStatus status,
                                                          WebRequest request) {


        ValidationErrorResponse validationError = new ValidationErrorResponse();
        ex.getFieldErrors().stream().forEach(validationError::add);

        Map<String, Object> errorAttributes = new HashMap<>();

        errorAttributes.put("path", ((ServletWebRequest) request).getRequest().getServletPath());
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("status", status);
        errorAttributes.put("error", validationError);

        return new ResponseEntity<>(errorAttributes, HttpStatus.BAD_REQUEST);
    }

}