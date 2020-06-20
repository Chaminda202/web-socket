package com.spring.websocket.handler;


import com.spring.websocket.exception.UserNotFoundException;
import com.spring.websocket.model.CustomErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> customHandleNotFound(Exception ex, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        //Get all field errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(errors)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgumentTypeMismatchException(Exception ex, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handel undefine attributes in json payload
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorDTO errorResponse = CustomErrorDTO.builder()
                .details(Arrays.asList(ex.getMessage()))
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
