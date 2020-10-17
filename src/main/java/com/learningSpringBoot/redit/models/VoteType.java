package com.learningSpringBoot.redit.models;

/**
 * Created by pc on 11/10/2020.
 */
public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;
    VoteType(int direction) {
    }
}
