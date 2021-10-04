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
    private static final String ROTATION_COMMANDS = "[LR]*";
    private static final String MOVEMENT_COMMANDS = "[FB]*";

    private Direction direction;
    private int x;
    private int y;

    public Rover(int x, int y, Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction must be specified at init time.");
        }
        this.direction = direction;
        this.x = x;
        this.y = y;
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
        String[] singleCommands = multipleCommands.split("");
        for (String singleCommand : singleCommands) {
            singleCommand(singleCommand);
        }
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

        if (commandString.matches(ROTATION_COMMANDS)) {
            RotationCommand rotationCommand = RotationCommand.valueOf(commandString);
            processDirectionCommand(rotationCommand);
        } else if (commandString.matches(MOVEMENT_COMMANDS)) {
            processMovementCommand(MovementCommand.valueOf(commandString));
        } else {
            throw new IllegalCommandInSequence(String.format("Command '%s' is invalid. " +
                    "Please consult manual for correct values.", commandString));
        }
        return printLocation();
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    String processMovementCommand(@NotNull MovementCommand movementCommand) {
        Objects.requireNonNull(movementCommand);
        switch (movementCommand) {
            case F:
                processDirectionCommandForward();
                break;
            case B:
                processDirectionCommandBackward();
                break;
            default:
                throw new RuntimeException("Unknown movementCommand");
        }
        return getDirection();
    }

    //    TODO add a check of integeroverflow for x and y
    void processDirectionCommandForward() {
        switch (direction) {
            case EAST:
                x++;
                break;
            case NORTH:
                y++;
                break;
            case WEST:
                x--;
                break;
            case SOUTH:
                y--;
                break;
        }
    }

    void processDirectionCommandBackward() {
        switch (direction) {
            case EAST:
                x--;
                break;
            case NORTH:
                y--;
                break;
            case WEST:
                x++;
                break;
            case SOUTH:
                y++;
                break;
        }
    }
}
