package com.horiaconstantin.kata.marsrover;

import java.util.Arrays;
import java.util.LinkedList;

public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;

	private final static LinkedList<Direction> directions = new LinkedList<>(Arrays.asList(NORTH, EAST, SOUTH, WEST));

	public static Direction turnLeft(Direction direction) {
		int idx = directions.indexOf(direction);
		Direction newDirection;
		if (idx == 0) {
			newDirection = directions.getLast();
		} else {
			newDirection = directions.get(idx - 1);
		}
		return newDirection;
	}

	public static Direction turnRight(Direction direction) {
		int idx = directions.indexOf(direction);
		Direction newDirection;
		if (idx == directions.size() - 1) {
			newDirection = directions.getFirst();
		} else {
			newDirection = directions.get(idx + 1);
		}
		return newDirection;
	}
}
