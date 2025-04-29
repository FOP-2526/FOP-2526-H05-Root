package h05.equipment;

import fopbot.Robot;
import h05.Equipment;
import h05.Equippable;

public class EquippedRobot extends Robot implements Equippable {

    public EquippedRobot(int x, int y) {
        super(x, y);
    }

    @Override
    public Equipment[] getEquipments() {
        return new Equipment[0];
    }

    @Override
    public Equipment getEquipment(int index) {
        return null;
    }

    @Override
    public Equipment getEquipment(String name) {
        return null;
    }

    @Override
    public void equip(Equipment equipment) {

    }

    @Override
    public void unequip(Equipment equipment) {

    }
}
