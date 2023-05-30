package com.alancss.devmoneyapi.service.exceptions;

public class PessoaInexistenteOuInativaException extends RuntimeException {
    public PessoaInexistenteOuInativaException(String message) {
        super(message);
    }
}
