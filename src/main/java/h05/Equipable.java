package h05;

public interface Equipable {

    Equipment[] getEquipment();

    void equip(Equipment equipment);

    void unequip(String name);

    void unequip(int index);
}
