package h05.game;

import fopbot.Direction;
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
            Map.entry(Direction.RIGHT, Set.of(KeyEvent.VK_RIGHT, KeyEvent.VK_D)),
            Map.entry(Direction.DOWN, Set.of(KeyEvent.VK_DOWN, KeyEvent.VK_S)),
            Map.entry(Direction.LEFT, Set.of(KeyEvent.VK_LEFT, KeyEvent.VK_A))
        ).entrySet()
        .stream()
        .flatMap(entry -> entry.getValue().stream().map(value -> Map.entry(value, entry.getKey())))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private static final Map<Integer, Integer> KEY_TO_SELECTION = Map.ofEntries(
        Map.entry(KeyEvent.VK_1, 1),
        Map.entry(KeyEvent.VK_2, 2),
        Map.entry(KeyEvent.VK_3, 3),
        Map.entry(KeyEvent.VK_4, 4),
        Map.entry(KeyEvent.VK_5, 5),
        Map.entry(KeyEvent.VK_6, 6),
        Map.entry(KeyEvent.VK_7, 7),
        Map.entry(KeyEvent.VK_8, 8),
        Map.entry(KeyEvent.VK_9, 9)
    );

    private final AtomicReference<Direction> direction = new AtomicReference<Direction>(null);

    private final AtomicInteger selection = new AtomicInteger(-1);

    private final AtomicBoolean pickGear = new AtomicBoolean(false);

    private final AtomicBoolean mine = new AtomicBoolean(false);

    private final AtomicBoolean info = new AtomicBoolean(false);

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
        this.info.set(keysPressed.contains(KeyEvent.VK_I));
        this.mine.set(keysPressed.contains(KeyEvent.VK_SPACE));
        this.pickGear.set(keysPressed.contains(KeyEvent.VK_E));
        this.direction.set(mapKeyToDirection(keysPressed));
        this.selection.set(mapKeyToSelection(keysPressed));
    }

    protected void updateKeysReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            mine.set(false);
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            pickGear.set(false);
        } else if (KEY_TO_DIRECTION.containsKey(e.getKeyCode()) &&
            direction.get() == KEY_TO_DIRECTION.get(e.getKeyCode())) {
            direction.set(null);
        } else if (KEY_TO_SELECTION.containsKey(e.getKeyCode()) &&
            selection.get() == KEY_TO_SELECTION.get(e.getKeyCode())) {
            selection.set(-1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_I) {
            info.set(false);
        }
    }


    public Direction getDirection() {
        Direction dir = direction.get();
        if (dir != null) {
            direction.set(null);
        }
        return dir;
    }

    public int getSelection() {
        int sel = selection.get();
        if (sel != -1) {
            selection.set(-1);
        }
        return sel;
    }

    public boolean isPickGear() {
        boolean isPick = pickGear.get();
        if (isPick) {
            pickGear.set(false);
        }
        return isPick;
    }

    public boolean isMine() {
        boolean isMine = mine.get();
        if (isMine) {
            this.mine.set(false);
        }
        return isMine;
    }

    public boolean isInfo() {
        boolean isInfo = info.get();
        if (isInfo) {
            this.info.set(false);
        }
        return isInfo;
    }

    public void install() {
        World.getGlobalWorld().getInputHandler().addKeyListener(
            new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    updateKeysPressed();
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    updateKeysReleased(e);
                }
            }
        );
    }
}
