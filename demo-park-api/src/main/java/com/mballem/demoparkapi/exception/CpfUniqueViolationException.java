package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class CpfUniqueViolationException extends RuntimeException {

    private String cpf;
    public CpfUniqueViolationException(String cpf) {
        this.cpf = cpf;
    }
}
