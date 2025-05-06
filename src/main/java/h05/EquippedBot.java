package h05;

import fopbot.Robot;
import fopbot.World;

public class EquippedBot extends Robot implements Equipable {

    private static final int MAX_EQUIPMENTS = 3;

    private Camera camera;
    private Battery battery;
    private final Equipment[] equipments = new Equipment[MAX_EQUIPMENTS];

    private int equipmentCount = 0;

    public EquippedBot(int x, int y) {
        super(x, y);
        this.camera = new Camera(x, y);
        this.battery = new Battery(x, y);
        removeFog(x, y);
        for (int[] points : getVisibleFields(x, y)) {
            removeFog(points[0], points[1]);
        }
    }

    private void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }

    private void placeFog(int x, int y) {
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    public int[][] getVisibleFields(int x, int y) {
        int visibilityRange = camera.getVisibilityRange();
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
    public void unequip(String name) {
        int i = 0;
        for (; i < equipmentCount; i++) {
            if (name.equals(equipments[i].getName())) {
                equipments[i] = null;
                equipmentCount--;
                break;
            }
        }
        for (; i < equipmentCount - 1; i++) {
            equipments[i] = equipments[i + 1];
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

    private void useBattery() {
        battery.reduceDurability(equipmentCount + 2);
    }

    @Override
    public void turnLeft() {
        if (isBatteryBroken()) {
            turnOff();
            return;
        }
        super.turnLeft();
        useBattery();
    }

    private void updateEquipmentsPosition() {
        for (int i = 0; i < equipmentCount; i++) {
            if (equipments[i] != null) {
                equipments[i].setX(getX());
                equipments[i].setY(getY());
            }
        }
    }

    public boolean isBatteryBroken() {
        return battery.getCondition() == Equipment.Condition.BROKEN;
    }

    @Override
    public void move() {
        if (isBatteryBroken()) {
            turnOff();
            return;
        }
        int oldX = getX();
        int oldY = getY();
        super.move();

        int newX = getX();
        int newY = getY();
        updateEquipmentsPosition();
        int[][] oldPoints = getVisibleFields(oldX, oldY);
        int[][] newPoints = getVisibleFields(getX(), getY());
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
        useBattery();
    }
}
