package com.alancss.devmoneyapi.service.exceptions;

public class CategoriaInexistenteException extends RuntimeException {

    public CategoriaInexistenteException(String message) {
        super(message);
    }
}
