package com.horiaconstantin.kata.marsrover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.horiaconstantin.kata.marsrover.RotationCommand.L;
import static com.horiaconstantin.kata.marsrover.RotationCommand.R;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoverTest {

    Rover rovy;

    @BeforeEach
    public void beforeEach() {
        rovy = new Rover(4, 2, Direction.NORTH);
    }

    @Test
    public void testConstructor() {
//        todo nulls and other strange values?
        assertNotNull(rovy);
    }

    @Test
    public void testGetDirection() {
        Rover rovy = new Rover(4, 2, Direction.EAST);

        assertEquals("EAST", rovy.getDirection());
    }

    @Test
    public void testConstructorWithInvalidDirection() {
        assertThrows(IllegalArgumentException.class, () -> new Rover(4, 2, null));
    }

    @Test
    public void testProcessDirectionCommandLeft() {
        Rover rovy = new Rover(4, 2, Direction.EAST);

        assertEquals("NORTH", rovy.processDirectionCommandLeft());
        assertEquals("WEST", rovy.processDirectionCommandLeft());
        assertEquals("SOUTH", rovy.processDirectionCommandLeft());
        assertEquals("EAST", rovy.processDirectionCommandLeft());
    }

    @Test
    public void testProcessDirectionCommand() {

        assertEquals("WEST", rovy.processDirectionCommand(L));
        assertEquals("SOUTH", rovy.processDirectionCommand(L));
        assertEquals("WEST", rovy.processDirectionCommand(R));
        assertEquals("NORTH", rovy.processDirectionCommand(R));
    }

    @Test
    public void testProcessDirectionCommandRight() {
        Rover rovy = new Rover(4, 2, Direction.WEST);

        assertEquals("NORTH", rovy.processDirectionCommandRight());
        assertEquals("EAST", rovy.processDirectionCommandRight());
        assertEquals("SOUTH", rovy.processDirectionCommandRight());
        assertEquals("WEST", rovy.processDirectionCommandRight());
    }

    @Test
    public void testSingleCommandEmptyOrNullOrInvalid() {
        rovy.singleCommand("");
        assertEquals("NORTH", rovy.getDirection());

        rovy.singleCommand(null);
        assertEquals("NORTH", rovy.getDirection());

        assertThrows(IllegalRotationCommand.class, () -> rovy.singleCommand("D"));
    }

    @Test
    public void testSingleCommandRotation() {
        rovy.singleCommand("L");
        assertEquals("WEST", rovy.getDirection());

        rovy.singleCommand("R");
        assertEquals("NORTH", rovy.getDirection());
    }

    @Test
    public void testMove() {
        rovy.move("LLRRLLL");

        assertEquals("EAST", rovy.getDirection());
    }

    @Test
    public void testMoveWithInvalidCommand() {
        assertThrows(IllegalCommandInSequence.class, () -> rovy.move("LLSRRLLL"));
    }

}
