package h05;

public interface Equippable {

    Equipment[] getEquipments();

    Equipment getEquipment(int index);

    Equipment getEquipment(String name);

    void equip(Equipment equipment);

    void unequip(Equipment equipment);
}
