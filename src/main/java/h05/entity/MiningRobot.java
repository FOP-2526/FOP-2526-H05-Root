package h05.entity;

import fopbot.*;
import h05.Equipment;
import h05.Miner;
import h05.UsableEquipment;
import h05.WorldUtilities;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Tool;
import h05.AbstractMinableEntity.AbstractMinableEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

import static h05.WorldUtilities.getPointInFront;

public class MiningRobot extends Robot implements Miner {

    private static final int MAX_EQUIPMENTS = 2;
    private static final int EQUIPMENTS_OFFSET = 2;
    private static final int INVENTORY_SIZE = 1000;

    private final Equipment[] storage = new Equipment[MAX_EQUIPMENTS + EQUIPMENTS_OFFSET];
    private final UsableEquipment[] usableEquipments = new UsableEquipment[MAX_EQUIPMENTS];
    private int currentInventory;
    private Tool primaryTool;


    private int equipmentCount;
    private int usableEquipmentsCount;

    public MiningRobot(int x, int y) {
        super(x, y);
        storage[0] = new Battery();
        storage[1] = new Camera();
        equipmentCount = EQUIPMENTS_OFFSET;
        currentInventory = 0;
        for (int[] points : getVision(getCamera().getVisibilityRange(), x, y)) {
            WorldUtilities.removeFog(points[0], points[1]);
        }
    }

    public Battery getBattery() {
        return (Battery) storage[0];
    }

    public Camera getCamera() {
        return (Camera) storage[1];
    }

    @Override
    public Equipment[] getStorage() {
        return Arrays.copyOf(storage, storage.length);
    }

    @Override
    public UsableEquipment[] getUsableEquipments() {
        return Arrays.copyOf(usableEquipments, usableEquipmentsCount);
    }

    @Override
    public void equip(Equipment equipment) {
        String name = equipment.getName();
        if (name.equals(getBattery().getName())) {
            storage[0] = equipment;
        } else if (name.equals(getCamera().getName())) {
            storage[1] = equipment;
        } else if (equipmentCount == storage.length) {
            storage[equipmentCount - 1] = equipment;
        } else {
            storage[equipmentCount++] = equipment;
        }
        if (equipment.isUsable()) {
            usableEquipments[usableEquipmentsCount++] = (UsableEquipment) equipment;
        }
    }

    @Override
    public void unequip(int index) {
        Equipment equipment = storage[index + EQUIPMENTS_OFFSET];
        if (equipment.isUsable()) {
            int i = 0;
            for (; i < usableEquipmentsCount; i++) {
                if (usableEquipments[i] == equipment) {
                    usableEquipments[i] = null;
                    return;
                }
            }
            for (; i < usableEquipmentsCount - 1; i++) {
                usableEquipments[i] = usableEquipments[i + 1];
            }
            storage[equipmentCount - 1] = null;
            usableEquipmentsCount--;
        }
        for (int i = index + EQUIPMENTS_OFFSET; i < equipmentCount - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[equipmentCount - 1] = null;
        equipmentCount--;
    }

    @Override
    public int getEquipmentCount() {
        return equipmentCount;
    }

    @Override
    public void useEquipment(int index) {
        if (index >= usableEquipmentsCount) {
            return;
        }
        getUsableEquipments()[index].use(getX(), getY(), getDirection());
    }

    public boolean isBatteryBroken() {
        return getBattery().getCondition() == Equipment.Condition.BROKEN;
    }

    private int[][] getVision(int visibilityRange, int x, int y) {
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


    void updateVision(int visibilityRange, int oldX, int oldY, int newX, int newY) {
        int[][] oldPoints = getVision(visibilityRange, oldX, oldY);
        int[][] newPoints = getVision(visibilityRange, newX, newY);
        for (int[] points : oldPoints) {
            int x = points[0];
            int y = points[1];
            if (x == newX && y == newY) {
                continue;
            }
            WorldUtilities.placeFog(x, y);
        }
        for (int[] points : newPoints) {
            WorldUtilities.removeFog(points[0], points[1]);
        }
    }

    @Override
    public void move() {
        if (isBatteryBroken()) {
            System.out.println("Battery is broken");
            return;
        }
        int oldX = getX();
        int oldY = getY();
        Direction direction = getDirection();
        int newX = oldX + direction.getDx();
        int newY = oldY + direction.getDy();
        int visibilityRange = getCamera().getVisibilityRange();

        super.move();

        updateVision(visibilityRange, oldX, oldY, newX, newY);
        getBattery().reduceDurability(equipmentCount + 2);
    }

    @Override
    public void mine() {
        var pointToMineAt = getPointInFront(getX(), getY(), getDirection());
        if (pointToMineAt == null) {
            return;
        }

        AbstractMinableEntity node = WorldUtilities.getNode(pointToMineAt.x, pointToMineAt.y);
        if (node == null) {
            return;
        }
        if (currentInventory >= INVENTORY_SIZE) {
            System.out.println("Inventory is full");
            return;
        }
        var amountMined = node.getMined(primaryTool);
        var inventoryAfterMining = currentInventory + amountMined;
        currentInventory = Math.min(inventoryAfterMining, INVENTORY_SIZE);
        System.out.println(node.getDurability());
        System.out.println(node.getState());

    }

    @Override
    public boolean isOnGear() {
        return WorldUtilities.isOnGear(getX(), getY());
    }

    @Override
    public void pickGear() {
        KarelWorld world = World.getGlobalWorld();
        for (FieldEntity entity : WorldUtilities.getEntities(getX(), getY())) {
            if (WorldUtilities.isGear(entity)) {
                world.removeEntity(entity);
                equip(((Gear) entity).getEquipment());
                return;
            }
            if (WorldUtilities.isTool(entity)) {
                primaryTool = (Tool) entity;
                world.removeEntity(entity);
            }
        }
        crash();
    }

    @Override
    public void handleKeyInput(@Nullable Direction direction, int selection, boolean pickGear, boolean mine) {
        if (mine) {
            mine();
        }
        if (direction != null) {
            while (getDirection() != direction) {
                turnLeft();
            }
            if (isFrontClear()) {
                move();
            }
        }
        if (selection != -1 && selection < getEquipmentCount()) {
            useEquipment(selection - 1);
        }
        if (pickGear) {
            pickGear();
        }
    }
}
