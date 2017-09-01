package com.test.vote.api.exception;


import com.google.common.collect.ImmutableList;
import com.test.vote.services.exception.EntityExistsException;
import com.test.vote.services.exception.EntityNotFoundException;
import com.test.vote.services.exception.IllegalTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleNotFoundException(Exception exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler({IllegalTimeException.class, EntityExistsException.class})
    public ResponseEntity handleIllegalArgumentException(Exception exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleExceptions(Exception exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Bad request " + request, ex);
        return ResponseEntity.badRequest().body(new ImmutableList.Builder<ValidationDetails>()
                .addAll(
                        ex.getBindingResult().getFieldErrors().stream()
                                .map(e -> new ValidationDetails(e.getField(), e.getDefaultMessage(), e.getRejectedValue()))
                                .collect(Collectors.toList())
                )
                .build());
    }
}

