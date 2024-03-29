package com.horiaconstantin.kata.marsrover;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.horiaconstantin.kata.marsrover.Direction.turnLeft;
import static com.horiaconstantin.kata.marsrover.Direction.turnRight;
import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;

public class Rover {

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

    public Rover(int x, int y, String directionString) {
        try {
            this.direction = Direction.valueOf(directionString);
            this.x = x;
            this.y = y;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(String.format("Direction '%s' invalid." +
                    "Consult manual for a valid direction string", directionString));
        }
    }


    public String getDirection() {
        return direction.name();
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
        return getLocationAsString();
    }

    private String getLocationAsString() {
        return String.format("(%s, %s) %s", x, y, direction);
    }

    /**
     * @return true if the rover moved, false otherwise
     */
    boolean singleCommand(String commandString) {
        if (StringUtils.isBlank(commandString)) {
            LOG.debug("Received empty move command, returning current coordinates");
            return false;
        }

        if (commandString.matches(ROTATION_COMMANDS)) {
            RotationCommand rotationCommand = RotationCommand.valueOf(commandString);
            processDirectionCommand(rotationCommand);
        } else if (commandString.matches(MOVEMENT_COMMANDS)) {
            processMovementCommand(MovementCommand.valueOf(commandString));
        } else {
            throw new IllegalCommandException(String.format("Command '%s' is invalid. " +
                    "Please consult manual for correct values.", commandString));
        }
        return true;
    }

    String processDirectionCommand(@NotNull RotationCommand rotate) {
        Objects.requireNonNull(rotate);
        switch (rotate) {
            case L:
                direction = turnLeft(direction);
                break;
            case R:
                direction = turnRight(direction);
                break;
            default:
                throw new RuntimeException("Unknown rotationCommand");
        }
        return getDirection();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    boolean processMovementCommand(@NotNull MovementCommand movementCommand) {
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
        return true;
    }

    void processDirectionCommandForward() {
        switch (direction) {
            case EAST:
                x = addOrSubtractOne(x, true);
                break;
            case NORTH:
                y = addOrSubtractOne(y, true);
                break;
            case WEST:
                x = addOrSubtractOne(x, false);
                break;
            case SOUTH:
                y = addOrSubtractOne(y, false);
                break;
        }
    }

    private int addOrSubtractOne(int value, boolean isAddition) {
        try {
            return isAddition ? addExact(value, 1) : subtractExact(value, 1);
        } catch (ArithmeticException ex) {
            throw new MovementException(ex.getMessage());
        }
    }

    void processDirectionCommandBackward() {
        switch (direction) {
            case EAST:
                x = addOrSubtractOne(x, false);
                break;
            case NORTH:
                y = addOrSubtractOne(y, false);
                break;
            case WEST:
                x = addOrSubtractOne(x, true);
                break;
            case SOUTH:
                y = addOrSubtractOne(y, true);
                break;
        }
    }


    public static void main(String[] args) {
        Rover rover = new Rover(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
        System.out.println(rover.move(args[3]));
    }
}
