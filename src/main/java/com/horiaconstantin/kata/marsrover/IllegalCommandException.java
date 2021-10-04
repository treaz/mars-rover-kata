package com.horiaconstantin.kata.marsrover;

public class IllegalCommandException extends RuntimeException {
    public IllegalCommandException(String message) {
        super(message);
    }
}
