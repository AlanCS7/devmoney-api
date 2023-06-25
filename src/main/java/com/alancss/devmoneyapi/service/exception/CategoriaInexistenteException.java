package com.alancss.devmoneyapi.service.exception;

public class CategoriaInexistenteException extends RuntimeException {

    public CategoriaInexistenteException(String message) {
        super(message);
    }
}
