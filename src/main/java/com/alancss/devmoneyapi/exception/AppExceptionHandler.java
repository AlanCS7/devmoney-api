package com.alancss.devmoneyapi.exception;

import com.alancss.devmoneyapi.service.exception.BusinessException;
import com.alancss.devmoneyapi.service.exception.CategoriaInexistenteException;
import com.alancss.devmoneyapi.service.exception.PessoaInexistenteOuInativaException;
import com.alancss.devmoneyapi.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public StandardError resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return new StandardError(
                Instant.now(),
                "Resource not found",
                Collections.singletonList(e.getMessage()),
                request.getRequestURI());
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            PessoaInexistenteOuInativaException.class,
            CategoriaInexistenteException.class,
            BusinessException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError badRequest(RuntimeException e, HttpServletRequest request) {
        return new StandardError(
                Instant.now(),
                "Bad Request",
                Collections.singletonList(e.getMessage()),
                request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError httpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        return new StandardError(
                Instant.now(),
                "Bad Request",
                Collections.singletonList(e.getMessage()),
                request.getRequestURI());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public StandardError usernameNotFoundException(UsernameNotFoundException e, HttpServletRequest request) {
        return new StandardError(
                Instant.now(),
                "Unauthorized",
                Collections.singletonList(e.getMessage()),
                request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        return new StandardError(
                Instant.now(),
                "Bad Request",
                e.getBindingResult().getFieldErrors().stream()
                        .map(error -> messageSource.getMessage(error, LocaleContextHolder.getLocale())).collect(Collectors.toList()),
                request.getRequestURI());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class StandardError {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        private Instant instant;
        private String error;
        private List<String> messages;
        private String path;
    }
}

