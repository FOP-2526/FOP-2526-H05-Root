package h05.game;

import fopbot.Direction;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameInputHandler {

    private static final Map<Integer, Direction> KEY_TO_DIRECTION = Map.ofEntries(
        Map.entry(KeyEvent.VK_UP, Direction.UP),
        Map.entry(KeyEvent.VK_W, Direction.UP),
        Map.entry(KeyEvent.VK_RIGHT, Direction.RIGHT),
        Map.entry(KeyEvent.VK_D, Direction.RIGHT),
        Map.entry(KeyEvent.VK_DOWN, Direction.DOWN),
        Map.entry(KeyEvent.VK_S, Direction.DOWN),
        Map.entry(KeyEvent.VK_LEFT, Direction.LEFT),
        Map.entry(KeyEvent.VK_A, Direction.LEFT)
    );

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

    private final Map<Integer, Boolean> keyState = new HashMap<>();

    private Direction direction = null;
    private int selection = -1;
    private boolean isPickingGear = false;
    private boolean isMining = false;
    private boolean isInfo = false;

    public void install() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            int code = e.getKeyCode();
            switch (e.getID()) {
                case KeyEvent.KEY_PRESSED -> keyState.put(code, true);
                case KeyEvent.KEY_RELEASED -> keyState.put(code, false);
            }
            refreshKeyState();
            return false;
        });
    }

    private void refreshKeyState() {
        isInfo = Boolean.TRUE.equals(keyState.get(KeyEvent.VK_I));
        isMining = Boolean.TRUE.equals(keyState.get(KeyEvent.VK_SPACE));
        isPickingGear = Boolean.TRUE.equals(keyState.get(KeyEvent.VK_E));

        var pressedDirections = keyState.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(e -> KEY_TO_DIRECTION.get(e.getKey()))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        direction = pressedDirections.size() == 1 ? pressedDirections.iterator().next() : null;

        var pressedSelections = keyState.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(e -> KEY_TO_SELECTION.get(e.getKey()))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        selection = pressedSelections.size() == 1 ? pressedSelections.iterator().next() : -1;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSelection() {
        return selection;
    }

    public boolean isPickingGear() {
        return isPickingGear;
    }

    public boolean isMining() {
        return isMining;
    }

    public boolean isInfo() {
        return isInfo;
    }
}
