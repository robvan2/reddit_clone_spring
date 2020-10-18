package com.learningSpringBoot.redit.exceptions;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String s) {
        super(s);
    }
}
