package h05;

import fopbot.Robot;

import java.util.Arrays;

public class EquippedRobot extends Robot implements Equippable {

    private static final int DEFAULT_CAPACITY = 10;

    private static final int GROWTH_RATE = 2;

    private RobotEquipment[] equipments = new RobotEquipment[DEFAULT_CAPACITY];
    private int next = 0;

    public EquippedRobot(int x, int y) {
        super(x, y);
    }

    @Override
    public RobotEquipment[] getEquipments() {
        return equipments;
    }

    @Override
    public RobotEquipment getEquipment(int index) {
        return equipments[index];
    }

    @Override
    public RobotEquipment getEquipment(String name) {
        for (int i = 0; i < next; i++) {
            if (equipments[i].getName().equals(name)) {
                return equipments[i];
            }
        }
        return null;
    }

    @Override
    public void equip(RobotEquipment equipment) {
        if (next == equipments.length) {
            equipments = Arrays.copyOf(equipments, equipments.length * GROWTH_RATE);
        }
        equipments[next++] = equipment;
    }
}
