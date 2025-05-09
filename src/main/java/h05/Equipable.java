package h05;

public interface Equipable {

    Equipment[] getStorage();

    UsableEquipment[] getUsableEquipments();

    int getEquipmentCount();

    void useEquipment(int index);

    void equip(Equipment equipment);

    void unequip(int index);
}
