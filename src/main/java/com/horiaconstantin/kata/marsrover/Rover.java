package com.horiaconstantin.kata.marsrover;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.horiaconstantin.kata.marsrover.Direction.EAST;
import static com.horiaconstantin.kata.marsrover.Direction.NORTH;
import static com.horiaconstantin.kata.marsrover.Direction.SOUTH;
import static com.horiaconstantin.kata.marsrover.Direction.WEST;

public class Rover {

    public static final String ALLOWS_COMMANDS = "[FBLR]*";
    private static final Logger LOG = LoggerFactory.getLogger(Rover.class);

    private Direction direction;

    public Rover(int i, int i1, Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction must be specified at init time.");
        }
        this.direction = direction;
    }


    public String getDirection() {
        return direction.name();
    }

    String processDirectionCommandLeft() {
//       TODO it would be nicer to have a circular linked list here...
        switch (direction) {
            case EAST:
                direction = NORTH;
                break;
            case NORTH:
                direction = WEST;
                break;
            case WEST:
                direction = SOUTH;
                break;
            case SOUTH:
                direction = EAST;
                break;
            default:
                throw new RuntimeException("Unknown direction");
        }
        return getDirection();
    }


    /**
     * @param multipleCommands a sequences of movement commands for the rover. For example: FBLFRBB
     * @return current rover coordinates and heading
     */
    public String move(String multipleCommands) {
        if (!multipleCommands.matches(ALLOWS_COMMANDS)) {
            throw new IllegalCommandInSequence(String.format("Command sequence '%s' contains invalid commands. " +
                    "Please consult manual for correct values.", multipleCommands));
        }

        String[] singleCommands = multipleCommands.split("");
        for (String singleCommand : singleCommands) {
            singleCommand(singleCommand);
        }
//        TODO move should return something reabalbe
        return printLocation();
    }

    private String printLocation() {
        return String.format("(4, 2) %s", direction);
    }

    String singleCommand(String commandString) {
        if (StringUtils.isBlank(commandString)) {
            LOG.debug("Received empty move command, returning current coordinates");
            return printLocation();
        }

        try {
            RotationCommand rotationCommand = RotationCommand.valueOf(commandString);
            processDirectionCommand(rotationCommand);
        } catch (IllegalArgumentException ex) {
            throw new IllegalRotationCommand(String.format("Invalid rotation command '%s'. " +
                    "Please consult manual for correct values", commandString));
        }
        return "";
    }

    String processDirectionCommand(@NotNull RotationCommand rotate) {
        Objects.requireNonNull(rotate);
        switch (rotate) {
            case L:
                processDirectionCommandLeft();
                break;
            case R:
                processDirectionCommandRight();
                break;
            default:
                throw new RuntimeException("Unknown rotationCommand");
        }
        return getDirection();
    }

    String processDirectionCommandRight() {
        switch (direction) {
            case EAST:
                direction = SOUTH;
                break;
            case NORTH:
                direction = EAST;
                break;
            case WEST:
                direction = NORTH;
                break;
            case SOUTH:
                direction = WEST;
                break;
            default:
                throw new RuntimeException("Unknown direction");
        }
        return getDirection();
    }

}
