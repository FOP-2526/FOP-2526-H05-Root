package h05.robot;

import fopbot.*;
//import h05.Pickaxe;
import h05.node.Rock;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class MiningRobot extends EquippedRobot implements KeyListener {
    private static final int DEFAULT_CAPACITY = 20;
    private int durability = 100;
//    private Pickaxe pickaxe;

    public MiningRobot(int x, int y) {
        super(x, y);
//        this.pickaxe = new Pickaxe();
        this.durability = 100;
    }

    public void mine() {
        int currentX = getX();
        int currentY = getY();
        int xToMine = currentX;
        int yToMine = currentY;
        if (isFacingUp()) {
            yToMine = currentY - 1;
        } else if (isFacingRight()) {
            xToMine = currentX + 1;
        } else if (isFacingDown()) {
            yToMine = currentY + 1;
        } else if (isFacingLeft()) {
            xToMine = currentX - 1;
        } else {
            return;
        }

        var world = World.getGlobalWorld();

        var field = world.getField(xToMine, yToMine);
        if (field == null) return;

        var entityList = field.getEntities();
        if (entityList.isEmpty()) return;

        var entity = entityList.getFirst();
        if (entity instanceof Rock node) {
//            node.mine();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        var direction = updateKeysPressed();
        switch (direction) {
            case LEFT -> {
                while (!isFacingLeft()) {
                    turnLeft();
                }
            }
            case RIGHT -> {
                while (!isFacingRight()) {
                    turnLeft();
                }
            }
            case UP -> {
                while (!isFacingUp()) {
                    turnLeft();
                }
            }
            case DOWN -> {
                while (!isFacingDown()) {
                    turnLeft();
                }
            }
            case SPACE -> {
                mine();
                var world = World.getGlobalWorld();

                return;
            }

            default -> {
                return;
            }
        }
        move();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    protected Key updateKeysPressed() {
         return (getDirectionFromKeysPressed(World.getGlobalWorld().getInputHandler().getKeysPressed()));
    }

    public static Key getDirectionFromKeysPressed(final Set<Integer> keysPressed) {
        final Map<Key, List<Integer>> directionKeys = Map.of(
                Key.UP, List.of(KeyEvent.VK_UP, KeyEvent.VK_W),
                Key.LEFT, List.of(KeyEvent.VK_LEFT, KeyEvent.VK_A),
                Key.DOWN, List.of(KeyEvent.VK_DOWN, KeyEvent.VK_S),
                Key.RIGHT, List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_D),
                Key.SPACE, List.of(KeyEvent.VK_SPACE)
        );
        final Set<Key> pressedDirections = new HashSet<>();
        for (final Key direction : directionKeys.keySet()) {
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

}
