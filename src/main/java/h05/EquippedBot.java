package h05;

import fopbot.Direction;
import fopbot.KarelWorld;
import fopbot.Robot;
import fopbot.World;

public class EquippedBot extends Robot implements Equipable {

    private static final int MAX_EQUIPMENTS = 3;

    private Tool tool;

    private final Equipment[] equipments = new Equipment[MAX_EQUIPMENTS];

    private int equipmentCount = 0;

    public EquippedBot(int x, int y, Direction direction) {
        super(x, y, direction, 0);
        this.tool = new NoTool(x, y);
        Camera camera = new Camera(x, y);
        equipments[0] = camera;
        equipments[1] = new Battery(x, y);
        equipmentCount = 2;

        removeFog(getVisibleFields());
    }

    public void removeFog(int[][] points) {
        KarelWorld world = World.getGlobalWorld();
        for (int[] point : points) {
            int newX = point[0];
            int newY = point[1];
            world.removeEntity(newX, newY, Fog.class);
        }
    }

    public void placeFog(int[][] points) {
        KarelWorld world = World.getGlobalWorld();
        for (int[] point : points) {
            int newX = point[0];
            int newY = point[1];
            world.placeEntity(new Fog(newX, newY));
        }
    }

    public int[][] getVisibleFields() {
        Camera camera = (Camera) equipments[0];
        int range = camera.getVisibilityRange();
        int x = getX();
        int y = getY();

        int count = 0;
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                count++;
            }
        }

        // Now create the array and fill it
        int[][] visibleFields = new int[count][2];
        int index = 0;
        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -range; dy <= range; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                visibleFields[index][0] = x + dx;
                visibleFields[index][1] = y + dy;
                index++;
            }
        }
        return visibleFields;
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
        placeFog(getVisibleFields());
        super.move();
        removeFog(getVisibleFields());
        for (int i = 0; i < equipmentCount; i++) {
            if (equipments[i] != null) {
                equipments[i].setX(getX());
                equipments[i].setY(getY());
            }
        }
    }
}
