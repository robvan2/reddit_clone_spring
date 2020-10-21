package com.learningSpringBoot.redit.exceptions;

public class VoteNotFoundException extends RuntimeException{
    public VoteNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
