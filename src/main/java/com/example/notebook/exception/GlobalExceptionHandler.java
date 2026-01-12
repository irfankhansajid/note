package com.example.notebook.exception;

import com.example.notebook.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(InvalidCredentialsException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOther(Exception ex) {
        return buildResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<ApiResponse<Object>> buildResponse(String message, HttpStatus status) {
        ApiResponse<Object> response = new ApiResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, status);
    }

}
