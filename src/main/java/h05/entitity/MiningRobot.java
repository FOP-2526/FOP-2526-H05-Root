package h05.entitity;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import h05.Equipment;
import h05.Fog;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.MiningDetector;
import h05.equipment.TelephotoLense;
import h05.equipment.WallBreaker;
import h05.ui.Equipable;

public class MiningRobot extends Robot implements Equipable {

    private static final int MAX_EQUIPMENTS = 2;

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

       for(int i = 0; i < equipmentCount; i++) {
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

        super.move();

        updateFog(visibilityRange, oldX, oldY, newX, newY);
        battery.reduceDurability(equipmentCount + 2);
    }
}
