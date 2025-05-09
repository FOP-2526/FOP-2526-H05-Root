package h05;

import fopbot.Direction;
import fopbot.Key;
import fopbot.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GameInputHandler {

    private static final Map<Integer, Direction> KEY_TO_DIRECTION = Map.ofEntries(
            Map.entry(Direction.UP, Set.of(KeyEvent.VK_UP, KeyEvent.VK_W)),
            Map.entry(Direction.RIGHT, Set.of(KeyEvent.VK_RIGHT, KeyEvent.VK_R)),
            Map.entry(Direction.DOWN, Set.of(KeyEvent.VK_DOWN, KeyEvent.VK_S)),
            Map.entry(Direction.LEFT, Set.of(KeyEvent.VK_LEFT, KeyEvent.VK_A))
        ).entrySet()
        .stream()
        .flatMap(entry -> entry.getValue().stream().map(value -> Map.entry(value, entry.getKey())))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static final Map<Integer, Integer> KEY_TO_SELECTION = Map.ofEntries(
        Map.entry(KeyEvent.VK_1, 1),
        Map.entry(KeyEvent.VK_2, 2),
        Map.entry(KeyEvent.VK_3, 3)
    );

    private final AtomicReference<Direction> direction = new AtomicReference<Direction>(null);

    private final AtomicInteger selection = new AtomicInteger(-1);

    private final AtomicBoolean pickGear = new AtomicBoolean(false);

    private final AtomicBoolean mine = new AtomicBoolean(false);

    private static @Nullable Direction mapKeyToDirection(Key key) {
        if (key == Key.UP) {
            return Direction.UP;
        } else if (key == Key.RIGHT) {
            return Direction.RIGHT;
        } else if (key == Key.DOWN) {
            return Direction.DOWN;
        } else if (key == Key.LEFT) {
            return Direction.LEFT;
        }
        return null;
    }

    protected @Nullable Direction mapKeyToDirection(Set<Integer> keysPressed) {
        @NotNull Set<Direction> directions = keysPressed.stream().map(KEY_TO_DIRECTION::get).collect(Collectors.toSet());
        return directions.size() == 1 ? directions.iterator().next() : null;
    }

    protected int mapKeyToSelection(Set<Integer> keysPressed) {
        @NotNull Set<Integer> choices = keysPressed.stream().map(KEY_TO_SELECTION::get).collect(Collectors.toSet());
        return choices.size() == 1 ? choices.iterator().next() : -1;
    }

    protected void updateKeysPressed() {
        Set<Integer> keysPressed = World.getGlobalWorld().getInputHandler().getKeysPressed();
        this.direction.set(mapKeyToDirection(keysPressed));
        this.selection.set(mapKeyToSelection(keysPressed));
        this.pickGear.set(keysPressed.contains(KeyEvent.VK_E));
        this.mine.set(keysPressed.contains(KeyEvent.VK_SPACE));
    }

    public Direction getDirection() {
        return direction.get();
    }

    public int getSelection() {
        return selection.get();
    }

    public boolean isMine() {
        return mine.get();
    }

    public void install() {
        World.getGlobalWorld().getInputHandler().addKeyListener(
            new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    updateKeysPressed();
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    updateKeysPressed();
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    updateKeysPressed();
                }
            }
        );
    }
}
