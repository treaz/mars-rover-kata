package com.horiaconstantin.kata.marsrover;

public class IllegalRotationCommand extends RuntimeException {
    public IllegalRotationCommand(String message) {
        super(message);
    }
}
