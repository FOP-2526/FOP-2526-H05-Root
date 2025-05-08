package h05.ui;

import h05.Equipment;

public interface Equipable {

    Equipment[] getEquipment();

    void equip(Equipment equipment);

    void unequip(int index);
}
