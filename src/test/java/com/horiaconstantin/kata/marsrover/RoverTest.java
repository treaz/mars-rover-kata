package com.horiaconstantin.kata.marsrover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.horiaconstantin.kata.marsrover.Direction.WEST;
import static com.horiaconstantin.kata.marsrover.RotationCommand.L;
import static com.horiaconstantin.kata.marsrover.RotationCommand.R;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThrows(IllegalArgumentException.class, () -> new Rover(4, 2, (Direction) null));
    }

    @Test
    public void testProcessDirectionCommand() {

        assertEquals("WEST", rovy.processDirectionCommand(L));
        assertEquals("SOUTH", rovy.processDirectionCommand(L));
        assertEquals("WEST", rovy.processDirectionCommand(R));
        assertEquals("NORTH", rovy.processDirectionCommand(R));
    }

    @Test
    public void testSingleCommandEmptyOrNullOrInvalid() {
        boolean singleCommand = rovy.singleCommand("");
        assertFalse(singleCommand);
        assertEquals("NORTH", rovy.getDirection());

        singleCommand = rovy.singleCommand(null);
        assertFalse(singleCommand);
        assertEquals("NORTH", rovy.getDirection());

        assertThrows(IllegalCommandException.class, () -> rovy.singleCommand("D"));
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
        assertThrows(IllegalCommandException.class, () -> rovy.move("LLSRRLLL"));
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

        rovy = new Rover(0, 0, WEST);
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

    @Test
    public void testProcessDirectionCommandBackward() {
        Rover rovy = new Rover(4, 2, Direction.EAST);

        rovy.processDirectionCommandBackward();
        assertEquals(3, rovy.getX());
        assertEquals(2, rovy.getY());

        rovy.processDirectionCommandBackward();
        assertEquals(2, rovy.getX());
        assertEquals(2, rovy.getY());

        rovy = new Rover(4, 2, Direction.NORTH);
        rovy.processDirectionCommandBackward();
        assertEquals(4, rovy.getX());
        assertEquals(1, rovy.getY());

        rovy = new Rover(0, 0, WEST);
        rovy.processDirectionCommandBackward();
        assertEquals(1, rovy.getX());
        assertEquals(0, rovy.getY());

        rovy = new Rover(-1111, -5, Direction.SOUTH);
        rovy.processDirectionCommandBackward();
        assertEquals(-1111, rovy.getX());
        assertEquals(-4, rovy.getY());
    }

    @Test
    public void testMoveBackward() {
        rovy.singleCommand("B");

        assertEquals(4, rovy.getX());
        assertEquals(1, rovy.getY());
    }

    @Test
    public void testProcessSingleCommandBackward() {
        rovy.move("BB");

        assertEquals(4, rovy.getX());
        assertEquals(0, rovy.getY());
    }

    @Test
    public void moveWithAllAvailableCommands() {
        Rover rovy = new Rover(0, 0, Direction.NORTH);

        assertEquals("(-2, 2) WEST", rovy.move("FLFFFRFLB"));
        assertEquals(-2, rovy.getX());
        assertEquals(2, rovy.getY());
        assertEquals("WEST", rovy.getDirection());

    }

    @Test
    public void moveOffGrid() {
        Rover rovy = new Rover(Integer.MAX_VALUE, Integer.MIN_VALUE, Direction.EAST);

        assertThrows(MovementException.class, () -> rovy.move("FFF"));

        Rover rovy2 = new Rover(Integer.MAX_VALUE, Integer.MIN_VALUE, Direction.NORTH);

        assertThrows(MovementException.class, () -> rovy2.move("B"));

    }

}
