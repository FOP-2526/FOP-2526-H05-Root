package h05.gamecontrol;

import fopbot.Direction;
import fopbot.World;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A {@link GameInputHandler} handles the input of the user.
 */
public class GameInputHandler {
    /**
     * The {@link Direction} to turn to. If {@code -1} the robot should not turn.
     */
    private final AtomicInteger direction = new AtomicInteger(-1);

    /**
     * If {@code true} the robot should mine the field in front.
     */
    private final AtomicBoolean shouldMine = new AtomicBoolean(false);


    /**
     * Parses the inputs to a direction.
     *
     * @param keysPressed the keys pressed
     * @return the direction or null if no direction is pressed
     */
    public static Direction getDirectionFromKeysPressed(final Set<Integer> keysPressed) {
        final Map<Direction, List<Integer>> directionKeys = Map.of(
            Direction.UP, List.of(KeyEvent.VK_UP, KeyEvent.VK_W),
            Direction.LEFT, List.of(KeyEvent.VK_LEFT, KeyEvent.VK_A),
            Direction.DOWN, List.of(KeyEvent.VK_DOWN, KeyEvent.VK_S),
            Direction.RIGHT, List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_D)
        );
        final Set<Direction> pressedDirections = new HashSet<>();
        for (final Direction direction : directionKeys.keySet()) {
            for (final Integer key : directionKeys.get(direction)) {
                if (keysPressed.contains(key)) {
                    pressedDirections.add(direction);
                }
            }
        }
        if (pressedDirections.size() == 1) {
            return pressedDirections.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Installs the {@link GameInputHandler} to the {@link World}.
     */
    public void install() {
        World.getGlobalWorld().getInputHandler().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                updateKeysPressed();
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                updateKeysPressed();
            }
        });
    }


    protected void updateKeysPressed() {
        this.direction.set(
            Optional.ofNullable(getDirectionFromKeysPressed(World.getGlobalWorld().getInputHandler().getKeysPressed()))
                .map(Enum::ordinal)
                .orElse(-1)
        );
        this.shouldMine.set(
            World.getGlobalWorld().getInputHandler().getKeysPressed().contains(KeyEvent.VK_SPACE));
    }



/**
 * Returns the current {@link Direction} to turn to. If {@code -1} the robot should not turn.
 *
 * @return the current {@link Direction} to turn to. If {@code -1} the robot should not turn.
 */
public int getDirection() {
    return this.direction.get();
}

public boolean getShouldMine() {
    return this.shouldMine.get();
}
}
