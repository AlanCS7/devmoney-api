package com.alancss.devmoneyapi.exceptions;

import com.alancss.devmoneyapi.service.exceptions.CategoriaInexistenteException;
import com.alancss.devmoneyapi.service.exceptions.PessoaInexistenteOuInativaException;
import com.alancss.devmoneyapi.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
            CategoriaInexistenteException.class
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

    private class StandardError {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        private Instant instant;
        private String error;
        private List<String> messages;
        private String path;

        public StandardError(Instant instant, String error, List<String> messages, String path) {
            this.instant = instant;
            this.error = error;
            this.messages = messages;
            this.path = path;
        }

        public Instant getInstant() {
            return instant;
        }

        public void setInstant(Instant instant) {
            this.instant = instant;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

