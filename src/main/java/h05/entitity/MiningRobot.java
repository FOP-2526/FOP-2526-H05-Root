package h05.entitity;

import fopbot.Direction;
import fopbot.Key;
import fopbot.Robot;
import fopbot.World;
import h05.Equipment;
import h05.equipment.Tool;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.MiningDetector;
import h05.equipment.TelephotoLense;
import h05.equipment.WallBreaker;
import h05.gamecontrol.TickBased;
import h05.node.Node;
import h05.ui.Equipable;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MiningRobot extends Robot implements Equipable, TickBased {

    private static final int MAX_EQUIPMENTS = 2;
    private static final int INVENTORY_SIZE = 1000;
    private int currentInventory = 0;

    private Camera camera;

    private Battery battery;

    private final Equipment[] equipments = new Equipment[MAX_EQUIPMENTS];

    private int equipmentCount = 0;

    public MiningRobot(int x, int y) {
        super(x, y);
        this.camera = new Camera();
        this.battery = new Battery();

        for (int[] points : getVisibleFields(camera.getVisibilityRange(), x, y)) {
            removeFog(points[0], points[1]);
        }
    }

    public Tool getPrimaryTool() {
        for (int i = 0; i < equipmentCount; i++) {
            Equipment equipment = equipments[i];
            if (equipment instanceof Tool) {
                return (Tool) equipment;
            }
        }
        return null;
    }

    void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }

    void placeFog(int x, int y) {
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    int[][] getVisibleFields(int visibilityRange, int x, int y) {
        int fieldCount = 0;
        for (int dx = -visibilityRange; dx <= visibilityRange; dx++) {
            for (int dy = -visibilityRange; dy <= visibilityRange; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                fieldCount++;
            }
        }

        int[][] visibleFields = new int[fieldCount][2];
        int index = 0;

        for (int dx = -visibilityRange; dx <= visibilityRange; dx++) {
            for (int dy = -visibilityRange; dy <= visibilityRange; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                visibleFields[index][0] = newX;
                visibleFields[index][1] = newY;
                index++;
            }
        }
        return visibleFields;
    }

    public Camera getCamera() {
        return camera;
    }

    public Battery getBattery() {
        return battery;
    }

    @Override
    public Equipment[] getEquipment() {
        return equipments;
    }

    @Override
    public void equip(Equipment equipment) {
        if (equipment.getName().equals(camera.getName())) {
            camera = (Camera) equipment;
        } else if (equipment.getName().equals(battery.getName())) {
            battery = (Battery) equipment;
        } else if (equipmentCount == equipments.length) {
            equipments[equipmentCount - 1] = equipment;
        } else {
            equipments[equipmentCount++] = equipment;
        }
    }

    @Override
    public void unequip(int index) {
        for (int i = index; i < equipmentCount - 1; i++) {
            equipments[i] = equipments[i + 1];
        }
        equipments[equipmentCount - 1] = null;
        equipmentCount--;
    }

    public boolean isBatteryBroken() {
        return battery.getCondition() == Equipment.Condition.BROKEN;
    }

    void updateFog(int visibilityRange, int oldX, int oldY, int newX, int newY) {
        int[][] oldPoints = getVisibleFields(visibilityRange, oldX, oldY);
        int[][] newPoints = getVisibleFields(visibilityRange, newX, newY);
        for (int[] points : oldPoints) {
            int x = points[0];
            int y = points[1];
            if (x == newX && y == newY) {
                continue;
            }
            placeFog(x, y);
        }
        for (int[] points : newPoints) {
            removeFog(points[0], points[1]);
        }
    }

    @Override
    public void move() {
        if (isBatteryBroken()) {
            return;
        }
        int oldX = getX();
        int oldY = getY();
        Direction direction = getDirection();
        int newX = oldX + direction.getDx();
        int newY = oldY + direction.getDy();
        int visibilityRange = camera.getVisibilityRange();

        for (int i = 0; i < equipmentCount; i++) {
            Equipment equipment = equipments[i];
            if (equipment.getName().equals("Wall Breaker")
                && !isFrontClear()
                && newX >= 0 && newX < World.getWidth()
                && newY >= 0 && newY < World.getHeight()) {
                ((WallBreaker) equipment).breakWall(newX, newY);
            } else if (equipment.getName().equals("TelephotoLense")) {
                visibilityRange = ((TelephotoLense) equipment).extendVisibilityRange(camera);
            } else if (equipment.getName().equals("Mining Detector")) {
                ((MiningDetector) equipment).scan(newX, newY);
            }
        }

        if (isFrontClear()) {
            super.move();
        }

        updateFog(visibilityRange, oldX, oldY, newX, newY);
        battery.reduceDurability(equipmentCount + 2);
    }

    public void updateInventoryAfterMine(int amountMined) {
        currentInventory += amountMined;
    }

    public void mine() {
        var pointToMineAt = getPointInFront();
        if (pointToMineAt == null) {
            return;
        }
        var entities = World.getGlobalWorld().getField(pointToMineAt.x, pointToMineAt.y).getEntities();
        if (entities.isEmpty()) {
            return;
        }
        var entityInFront = entities.getFirst();

        Node node = (Node) entityInFront;
        var primaryTool = getPrimaryTool();

        var amountMined = node.getMined(primaryTool);
        var inventoryAfterMining = currentInventory + amountMined;
        currentInventory = Math.min(inventoryAfterMining, INVENTORY_SIZE);
        System.out.println(node.getDurability());
        System.out.println(node.getMiningState());
    }

    public Point getPointInFront() {
        int x = getX();
        int y = getY();
        Direction direction = getDirection();
        int newX = x + direction.getDx();
        int newY = y + direction.getDy();
        if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
            return null;
        }
        return new Point(newX, newY);
    }

    public static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void handleKeyInput(final int direction, final boolean shouldMine) {
        if (shouldMine) {
            mine();
        }
        if (direction >= 0 && direction < 4) {
            // start with direction 0 (UP)
            int dx = 0;
            int dy = 1;
            // rotate direction times by 90 degrees to the right
            for (int i = 0; i < direction; i++) {
                final int tmp = dx;
                dx = dy;
                dy = -tmp;
            }
            while (getDirection().getDx() != dx || getDirection().getDy() != dy) {
                turnLeft();
            }
            if (isFrontClear()) {
                move();
            }

        }
    }
}
