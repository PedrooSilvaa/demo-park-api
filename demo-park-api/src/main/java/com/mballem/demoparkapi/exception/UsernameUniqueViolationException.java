package com.mballem.demoparkapi.exception;

import lombok.Getter;

@Getter
public class UsernameUniqueViolationException extends RuntimeException {

    private String username;

    public UsernameUniqueViolationException(String username) {
        this.username = username;
    }
}
