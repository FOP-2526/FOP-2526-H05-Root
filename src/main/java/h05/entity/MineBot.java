package h05.entity;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import h05.base.game.GameSettings;
import h05.base.mineable.BasicInventory;
import h05.base.mineable.Inventory;
import h05.base.ui.InfoPopup;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Equipment;
import h05.equipment.Tool;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

@DoNotTouch
public class MineBot extends Robot implements Miner {

    @DoNotTouch
    public static final int DEFAULT_CAPACITY = 4;

    @DoNotTouch
    private final @NotNull GameSettings settings;

    @DoNotTouch
    private final @NotNull Equipment[] equipments;
    @DoNotTouch
    private final @NotNull Inventory inventory;
    @DoNotTouch
    private int nextIndex;
    @DoNotTouch
    private @NotNull Battery battery;
    @DoNotTouch
    private @NotNull Camera camera;
    @DoNotTouch
    private @Nullable Tool tool;

    @DoNotTouch
    public MineBot(int x, int y, @NotNull GameSettings settings, int capacity) {
        super(x, y);
        this.settings = settings;
        this.equipments = new Equipment[capacity];
        this.battery = new Battery();
        this.camera = new Camera();
        this.equipments[0] = this.battery;
        this.equipments[1] = this.camera;
        nextIndex = 2;
        this.inventory = new BasicInventory();
        for (Point point : getVision(x, y)) {
            settings.removeFog(point.x, point.y);
        }
    }

    @DoNotTouch
    public MineBot(int x, int y, @NotNull GameSettings settings) {
        this(x, y, settings, DEFAULT_CAPACITY);
    }

    @StudentImplementationRequired("H5.4.2")
    @Override
    public @NotNull Point[] getVision(int x, int y) {
        int visibilityRange = camera.getVisibilityRange();
        int fieldCount = 0;
        for (int dx = -visibilityRange; dx <= visibilityRange; dx++) {
            int newX = x + dx;
            if (newX < 0 || newX >= World.getWidth()) {
                continue;
            }
            for (int dy = -visibilityRange; dy <= visibilityRange; dy++) {

                int newY = y + dy;
                if (newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                fieldCount++;
            }
        }

        Point[] points = new Point[fieldCount];
        int index = 0;

        for (int dx = -visibilityRange; dx <= visibilityRange; dx++) {
            for (int dy = -visibilityRange; dy <= visibilityRange; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                points[index] = new Point(newX, newY);
                index++;
            }
        }
        return points;
    }


    @StudentImplementationRequired("H5.4.2")
    public void updateVision(int oldX, int oldY, int newX, int newY) {
        Point[] oldPoints = getVision(oldX, oldY);
        Point[] newPoints = getVision(newX, newY);
        for (Point point : oldPoints) {
            int x = point.x;
            int y = point.y;
            if (x == newX && y == newY) {
                continue;
            }
            settings.placeFog(x, y);
        }
        for (Point point : newPoints) {
            settings.removeFog(point.x, point.y);
        }
    }

    @StudentImplementationRequired("H05.4.3")
    @Override
    public void move() {
        if (isBatteryBroken()) {
            return;
        }
        int oldX = getX();
        int oldY = getY();
        Direction direction = getDirection();
        int newX = oldX + direction.getDx();
        int newY = oldY + direction.getDy();

        super.move();

        updateVision(oldX, oldY, newX, newY);
        getBattery().reduceDurability(getNumberOfEquipments());
    }

    @Override
    public @NotNull GameSettings getGameSettings() {
        return settings;
    }

    @DoNotTouch
    @Override
    public Equipment[] getEquipments() {
        Equipment[] equipments = new Equipment[nextIndex];
        System.arraycopy(this.equipments, 0, equipments, 0, nextIndex);
        return equipments;
    }

    @DoNotTouch
    @Override
    public int getNumberOfEquipments() {
        return nextIndex + 2 + (tool == null ? 0 : 1);
    }

    @StudentImplementationRequired("H5.4.4")
    @Override
    public void use(int index) {
        int currentIndex = 0;
        for (int i = 0; i < nextIndex; i++) {
            Equipment equipment = equipments[i];
            if (equipment.isUsable()) {
                if (currentIndex == index) {
                    settings.toUsableEquipment(equipment).use(this);
                    if (equipment.getName().equals("TelephotoLens")) {
                        int x = getX();
                        int y = getY();
                        updateVision(x, y, x, y);
                    }
                }
                currentIndex++;
            }
        }
    }

    @DoNotTouch
    @Override
    public void equip(@NotNull Equipment equipment) {
        if (equipment.getName().equals("Battery")) {
            Battery newBattery = settings.toBattery(equipment);
            if (newBattery != null) {
                battery = newBattery;
                equipments[0] = newBattery;
            }
        } else if (equipment.getName().equals("Camera")) {
            Camera newCamera = settings.toCamery(equipment);
            if (newCamera != null) {
                camera = newCamera;
                equipments[1] = newCamera;
            }
        } else {
            for (int i = 2; i < nextIndex; i++) {
                if (equipments[i].getName().equals(equipment.getName())) {
                    equipments[i] = equipment;
                    return;
                }
            }
            if (nextIndex + (tool == null ? 0 : 1) == equipments.length) {
                return;
            }
            if (equipment.isTool()) {
                tool = settings.toTool(equipment);
            }
            equipments[nextIndex] = equipment;
            nextIndex++;
        }
    }

    @DoNotTouch
    @Override
    public void unequip(int index) {
        if (index + 2 < nextIndex) {
            return;
        }
        for (int i = index + 2; i < nextIndex - 1; i++) {
            equipments[i] = equipments[i + 1];
        }
        equipments[nextIndex - 1] = null;
        nextIndex--;
    }

    @Override
    public @NotNull Battery getBattery() {
        return battery;
    }

    @Override
    public @NotNull Camera getCamera() {
        return camera;
    }

    @Override
    public @Nullable Tool getTool() {
        return tool;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @StudentImplementationRequired("H5.4.1")
    @Override
    public void mine() {
        Direction direction = getDirection();
        int x = getX() + direction.getDx();
        int y = getY() + direction.getDy();

        if (x < 0 || x > World.getWidth() || y < 0 || y >= World.getWidth()) {
            return;
        }
        Mineable mineable = settings.getLootAt(x, y);
        if (mineable == null) {
            return;
        }
        if (mineable.onMined(tool)) {
            if (!inventory.add(mineable)) {
                crash();
            }
        }
    }

    @DoNotTouch
    @Override
    public void pickGear() {
        Equipment equipment = settings.getAndRemoveGearAt(getX(), getY());
        if (equipment != null) {
            equip(equipment);
        }
    }

    @DoNotTouch
    @Override
    public void handleKeyInput(@Nullable Direction direction, int selection, boolean isPickingGear, boolean isMining, boolean isInfo) {
        if (direction != null) {
            while (getDirection() != direction) {
                turnLeft();
            }
            if (isFrontClear()) {
                move();
            }
        }
        if (selection != -1) {
            use(selection - 1);
        }
        if (isPickingGear) {
            pickGear();
        }
        if (isMining) {
            mine();
        }
        if (isInfo) {
            InfoPopup.showInfo(inventory);
        }
    }
}
