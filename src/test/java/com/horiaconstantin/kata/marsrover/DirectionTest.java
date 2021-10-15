package com.horiaconstantin.kata.marsrover;

import org.junit.jupiter.api.Test;

import static com.horiaconstantin.kata.marsrover.Direction.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

	@Test
	public void testTurnRight() {
		assertEquals(NORTH, turnRight(WEST));
		assertEquals(EAST, turnRight(NORTH));
		assertEquals(SOUTH, turnRight(EAST));
		assertEquals(WEST, turnRight(SOUTH));
	}

	@Test
	public void testProcessDirectionCommandLeft() {
		assertEquals(NORTH, turnLeft(EAST));
		assertEquals(WEST, turnLeft(NORTH));
		assertEquals(SOUTH, turnLeft(WEST));
		assertEquals(EAST, turnLeft(SOUTH));
	}

}