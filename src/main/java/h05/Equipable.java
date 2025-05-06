package h05;

public interface Equipable {

    Tool getTool();

    Equipment[] getEquipment();

    void equip(Tool tool);

    void equip(Equipment equipment);

    void unequip(Tool tool);

    void unequip(Equipment equipment);
}
