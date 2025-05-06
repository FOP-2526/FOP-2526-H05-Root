package h05;

public interface Equipable {

    Tool getTool();

    Equipment[] getEquipment();

    void equip(Tool tool);

    void equip(Equipment equipment);

    void unequip(String name);

    void unequip(int index);
}
