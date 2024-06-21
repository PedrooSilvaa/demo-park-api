package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class InvalidCredencialException extends RuntimeException {

    private String username;

    public InvalidCredencialException(String username) {
        this.username = username;
    }
}
