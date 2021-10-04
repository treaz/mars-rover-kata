package com.horiaconstantin.kata.marsrover;

public class IllegalCommandInSequence extends RuntimeException {
    public IllegalCommandInSequence(String message) {
        super(message);
    }
}
