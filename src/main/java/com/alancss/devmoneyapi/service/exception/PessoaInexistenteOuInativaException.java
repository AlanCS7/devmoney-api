package com.alancss.devmoneyapi.service.exception;

public class PessoaInexistenteOuInativaException extends RuntimeException {
    public PessoaInexistenteOuInativaException(String message) {
        super(message);
    }
}
