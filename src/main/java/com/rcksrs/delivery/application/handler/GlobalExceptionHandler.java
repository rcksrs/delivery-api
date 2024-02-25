package com.rcksrs.delivery.application.handler;

import com.rcksrs.delivery.core.exception.global.BusinessException;
import com.rcksrs.delivery.core.exception.global.EntityNotFoundException;
import com.rcksrs.delivery.core.exception.global.ExceptionMessage;
import com.rcksrs.delivery.core.exception.global.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ExceptionMessage>> handler(MethodArgumentNotValidException ex) {
        log.info("Exception caught while validating data. {}", ex.getMessage());

        List<ExceptionMessage> errors = ex.getFieldErrors()
                .stream()
                .map(e -> new ExceptionMessage(e.getDefaultMessage(), e.getField()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionMessage> handleUnauthorizedException(UnauthorizedException ex) {
        log.info("Access denied. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.info("Resource not found for the provided field. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handleBusinessException(BusinessException ex) {
        log.info("Unable to process the request. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        log.info("Authentication exception. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handleBadCredentialsException(BadCredentialsException ex) {
        log.info("Invalid credentials. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionMessage> handleAccessDeniedException(AccessDeniedException ex) {
        log.info("Invalid permission. {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionMessage(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionMessage> handleException(Exception ex) {
        log.error("Unexpected error. Unable to process the request.", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionMessage(ex.getMessage()));
    }
}
