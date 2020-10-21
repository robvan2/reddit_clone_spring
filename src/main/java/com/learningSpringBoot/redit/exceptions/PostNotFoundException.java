package com.learningSpringBoot.redit.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
