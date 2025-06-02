package h05.entity;

import fopbot.Robot;
import h05.gear.Equipment;
import h05.gear.UsableEquipment;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class EquippedRobot extends Robot implements Equipable {

    private final Equipment[] equipments;
    private final UsableEquipment[] usableEquipments;
    protected int equipmentCount;
    protected int usableEquipmentCount;
    private final int capacity;

    public EquippedRobot(int x, int y, int capacity) {
        super(x, y);
        this.equipments = new Equipment[capacity];
        this.usableEquipments = new UsableEquipment[capacity];
        this.capacity = capacity;
    }

    @Override
    public Equipment[] getEquipments() {
        int length = equipmentCount + usableEquipmentCount;
        Equipment[] equipments = new Equipment[length];
        System.arraycopy(this.equipments, 0, equipments, 0, equipmentCount);
        System.arraycopy(usableEquipments, 0, equipments, equipmentCount, usableEquipmentCount);
        return equipments;
    }

    public void setEquipment(int index, Equipment equipment) {
        equipments[index] = equipment;
    }

    @Override
    public int getNumberOfEquipments() {
        return equipmentCount + usableEquipmentCount;
    }

    @Override
    public UsableEquipment[] getUsableEquipments() {
        return Arrays.copyOf(usableEquipments, usableEquipmentCount);
    }

    @Override
    public void useEquipment(int index) {
        if (usableEquipmentCount == 0) {
            return;
        }
        usableEquipments[index].use(getX(), getY(), getDirection());
    }

    @Override
    public void equip(@NotNull Equipment equipment) {
        if (equipmentCount + usableEquipmentCount == capacity) {
            return;
        }
        if (equipment.isUsable()) {
            usableEquipments[usableEquipmentCount++] = (UsableEquipment) equipment;
        } else {
            equipments[equipmentCount++] = equipment;
        }
    }

    @Override
    public void unequip(int index) {
        int totalEquipments = equipmentCount + usableEquipmentCount;
        if (index < equipmentCount) {
            // Remove from non-usable equipment
            for (int i = index; i < equipmentCount - 1; i++) {
                equipments[i] = equipments[i + 1];
            }
            equipments[--equipmentCount] = null;
        } else {
            // Remove from usable equipment
            int usableIndex = index - equipmentCount;
            for (int i = usableIndex; i < usableEquipmentCount - 1; i++) {
                usableEquipments[i] = usableEquipments[i + 1];
            }
            usableEquipments[--usableEquipmentCount] = null;
        }
    }
}
