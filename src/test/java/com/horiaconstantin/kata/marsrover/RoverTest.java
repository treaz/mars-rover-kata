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

        assertThrows(IllegalCommandInSequence.class, () -> rovy.singleCommand("D"));
    }

    @Test
    public void testSingleCommandRotation() {
        rovy.singleCommand("L");
        assertEquals("WEST", rovy.getDirection());

        rovy.singleCommand("R");
        assertEquals("NORTH", rovy.getDirection());
    }

    @Test
    public void testMoveRotation() {
        rovy.move("LLRRLLL");

        assertEquals("EAST", rovy.getDirection());
    }

    @Test
    public void testMoveWithInvalidCommand() {
        assertThrows(IllegalCommandInSequence.class, () -> rovy.move("LLSRRLLL"));
    }

    @Test
    public void testMoveOutput() {
        assertEquals("(4, 2) NORTH", rovy.move(""));
        assertEquals("(4, 2) EAST", rovy.move("LLL"));
    }

    @Test
    public void testCoords() {
        Rover rovy = new Rover(5, -3, Direction.EAST);

        assertEquals(5, rovy.getX());
        assertEquals(-3, rovy.getY());
    }

    @Test
    public void testProcessDirectionCommandForward() {
        Rover rovy = new Rover(4, 2, Direction.EAST);

        rovy.processDirectionCommandForward();
        assertEquals(5, rovy.getX());
        assertEquals(2, rovy.getY());

        rovy.processDirectionCommandForward();
        assertEquals(6, rovy.getX());
        assertEquals(2, rovy.getY());

        rovy = new Rover(4, 2, Direction.NORTH);
        rovy.processDirectionCommandForward();
        assertEquals(4, rovy.getX());
        assertEquals(3, rovy.getY());

        rovy = new Rover(0, 0, Direction.WEST);
        rovy.processDirectionCommandForward();
        assertEquals(-1, rovy.getX());
        assertEquals(0, rovy.getY());

        rovy = new Rover(-1111, -5, Direction.SOUTH);
        rovy.processDirectionCommandForward();
        assertEquals(-1111, rovy.getX());
        assertEquals(-6, rovy.getY());
    }

    @Test
    public void testProcessSingleCommandForward() {
        rovy.singleCommand("F");

        assertEquals(4, rovy.getX());
        assertEquals(3, rovy.getY());
    }

    @Test
    public void testMoveForward() {
        rovy.move("FFFFFF");

        assertEquals(4, rovy.getX());
        assertEquals(8, rovy.getY());
    }


}
