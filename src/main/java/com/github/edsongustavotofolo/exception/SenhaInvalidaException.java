package com.github.edsongustavotofolo.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
