package h05.entity;

import fopbot.Robot;
import h05.gear.Equipment;
import h05.gear.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Arrays;

/**
 * Represents a robot that can be equipped with various types of equipment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class EquippedRobot extends Robot implements Equipable {

    /**
     * The equipment slots available for this robot.
     */
    @DoNotTouch
    private final Equipment[] equipments;

    /**
     * The usable equipment slots available for this robot.
     */
    @DoNotTouch
    private final UsableEquipment[] usableEquipments;

    /**
     * The number of non-usable equipments currently equipped.
     */
    @DoNotTouch
    protected int equipmentCount;

    /**
     * The number of usable equipments currently equipped.
     */
    @DoNotTouch
    protected int usableEquipmentCount;

    /**
     * The maximum capacity of the robot for equipment.
     */
    @DoNotTouch
    private final int capacity;

    /**
     * Constructs a new {@link EquippedRobot} instance with the specified position and capacity.
     * @param x the x-coordinate of the robot
     * @param y the y-coordinate of the robot
     * @param capacity the maximum number of equipment slots available for this robot
     */
    @DoNotTouch
    public EquippedRobot(int x, int y, int capacity) {
        super(x, y);
        this.equipments = new Equipment[capacity];
        this.usableEquipments = new UsableEquipment[capacity];
        this.capacity = capacity;
    }

    @DoNotTouch
    @Override
    public Equipment[] getEquipments() {
        int length = equipmentCount + usableEquipmentCount;
        Equipment[] equipments = new Equipment[length];
        System.arraycopy(this.equipments, 0, equipments, 0, equipmentCount);
        System.arraycopy(usableEquipments, 0, equipments, equipmentCount, usableEquipmentCount);
        return equipments;
    }

    /**
     * Sets the equipment at the specified index.
     * @param index the index of the equipment to set
     * @param equipment the equipment to set at the specified index
     */
    @DoNotTouch
    public void setEquipment(int index, Equipment equipment) {
        equipments[index] = equipment;
    }

    @DoNotTouch
    @Override
    public int getNumberOfEquipments() {
        return equipmentCount + usableEquipmentCount;
    }

    @DoNotTouch
    @Override
    public UsableEquipment[] getUsableEquipments() {
        return Arrays.copyOf(usableEquipments, usableEquipmentCount);
    }

    @DoNotTouch
    @Override
    public void useEquipment(int index) {
        if (usableEquipmentCount == 0) {
            return;
        }
        usableEquipments[index].use(getX(), getY(), getDirection());
    }

    @DoNotTouch
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

    @DoNotTouch
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
