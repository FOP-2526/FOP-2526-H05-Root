package h05.entitity;

import fopbot.Robot;
import fopbot.World;
import h05.Battery;
import h05.Camera;
import h05.Equipment;
import h05.Fog;
import h05.ui.Equipable;

public class MineBot extends Robot implements Equipable {

    private static final int MAX_EQUIPMENTS = 3;

    private Camera camera;

    private Battery battery;

    private final Equipment[] equipments = new Equipment[MAX_EQUIPMENTS];

    private int equipmentCount = 0;

    public MineBot(int x, int y) {
        super(x, y);
        this.camera = new Camera();
        this.battery = new Battery();

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

    @Override
    public void move() {
        if (isBatteryBroken()) {
            return;
        }
        int oldX = getX();
        int oldY = getY();
        super.move();


        int newX = getX();
        int newY = getY();
        int[][] oldPoints = getVisibleFields(oldX, oldY);
        int[][] newPoints = getVisibleFields(newX, newY);
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
        battery.reduceDurability(equipmentCount + 2);
    }
}
