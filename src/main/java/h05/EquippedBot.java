package h05;

import fopbot.Direction;
import fopbot.Robot;

public class EquippedBot extends Robot implements Equipable {

    private static final int MAX_EQUIPMENTS = 3;

    private Tool tool;

    private final Equipment[] equipments = new Equipment[MAX_EQUIPMENTS];

    private int equipmentCount = 0;

    public EquippedBot(int x, int y, Direction direction) {
        super(x, y, direction, 0);
        this.tool = new NoTool(x, y);
        equipments[0] = new Camera(x, y);
        equipments[1] = new Battery(x, y);
        equipmentCount = 2;
    }

    @Override
    public Tool getTool() {
        return tool;
    }

    @Override
    public Equipment[] getEquipment() {
        return equipments;
    }

    @Override
    public void equip(Tool tool) {
        this.tool = tool;
    }

    @Override
    public void equip(Equipment equipment) {
        if (equipmentCount == equipments.length) {
            equipments[equipmentCount - 1] = equipment;
        } else {
            equipments[equipmentCount++] = equipment;
        }
    }

    @Override
    public void unequip(String name) {
        int i = 0;
        for (; i < equipmentCount; i++) {
            if (name.equals(equipments[i].getName())) {
                equipments[i] = null;
                equipmentCount--;
                break;
            }
        }
        for (; i < equipmentCount - 1; i++) {
            equipments[i] = equipments[i + 1];
        }
    }

    @Override
    public void unequip(int index) {
        for (int i = index; i < equipmentCount - 1; i++) {
            equipments[i] = equipments[i + 1];
        }
        equipments[equipmentCount - 1] = null;
        equipmentCount--;
    }

    @Override
    public void move() {
        super.move();
        for(int i = 0; i < equipmentCount; i++) {
            if (equipments[i] != null) {
                equipments[i].setX(getX());
                equipments[i].setY(getY());
            }
        }
    }
}
