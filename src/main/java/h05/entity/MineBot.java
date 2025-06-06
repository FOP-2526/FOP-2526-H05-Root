package h05.entity;

import fopbot.Direction;
import fopbot.World;
import h05.WorldUtilities;
import h05.gear.Tool;
import h05.loot.Mineable;
import h05.ui.InfoPopup;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

/**
 * A basic implementation of a mining robot that can mine resources in the game world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class MineBot extends AbstractMiningRobot implements Miner {

    /**
     * The default capacity for the equipment of the mining bot.
     */
    @DoNotTouch
    public static final int EQUIPMENT_DEFAULT_CAPACITY = 5;

    /**
     * The default capacity for the inventory of the mining bot.
     */
    @DoNotTouch
    public static final int INVENTORY_DEFAULT_CAPACITY = 5;

    /**
     * Constructs a new {@link MineBot} instance with the specified position, equipment capacity, and inventory capacity.
     *
     * @param x                 the x-coordinate of the mining bot
     * @param y                 the y-coordinate of the mining bot
     * @param equipmentCapacity the capacity for the equipment of the mining bot
     * @param inventoryCapacity the capacity for the inventory of the mining bot
     */
    @DoNotTouch
    public MineBot(int x, int y, int equipmentCapacity, int inventoryCapacity) {
        super(x, y, equipmentCapacity, inventoryCapacity);
        for (Point point : getVision(getCamera().getVisibilityRange(), x, y)) {
            WorldUtilities.removeFog(point.x, point.y);
        }
    }

    /**
     * Constructs a new {@link MineBot} instance with the specified position and default capacities for equipment and
     * inventory.
     *
     * @param x the x-coordinate of the mining bot
     * @param y the y-coordinate of the mining bot
     */
    @DoNotTouch
    public MineBot(int x, int y) {
        this(x, y, EQUIPMENT_DEFAULT_CAPACITY, INVENTORY_DEFAULT_CAPACITY);
    }

    /**
     * Returns the vision of the mining bot based on its visibility range and position.
     *
     * @param visibilityRange the visibility range of the mining bot
     * @param x               the x-coordinate of the mining bot
     * @param y               the y-coordinate of the mining bot
     *
     * @return an array of points representing the vision of the mining bot
     */
    @StudentImplementationRequired("H05.4.1")
    private Point[] getVision(int visibilityRange, int x, int y) {
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

    /**
     * Updates the vision of the mining bot by removing fog from the old position and adding fog to the new position.
     *
     * @param visibilityRange the visibility range of the mining bot
     * @param oldX            the old x-coordinate of the mining bot
     * @param oldY            the old y-coordinate of the mining bot
     * @param newX            the new x-coordinate of the mining bot
     * @param newY            the new y-coordinate of the mining bot
     */
    @StudentImplementationRequired("H05.4.1")
    void updateVision(int visibilityRange, int oldX, int oldY, int newX, int newY) {
        Point[] oldPoints = getVision(visibilityRange, oldX, oldY);
        Point[] newPoints = getVision(visibilityRange, newX, newY);
        for (Point point : oldPoints) {
            int x = point.x;
            int y = point.y;
            if (x == newX && y == newY) {
                continue;
            }
            WorldUtilities.placeFog(x, y);
        }
        for (Point point : newPoints) {
            WorldUtilities.removeFog(point.x, point.y);
        }
    }

    @StudentImplementationRequired("H05.4.2")
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
        int visibilityRange = getCamera().getVisibilityRange();

        super.move();

        updateVision(visibilityRange, oldX, oldY, newX, newY);
        getBattery().reduceDurability(getNumberOfEquipments());
    }

    @StudentImplementationRequired("H05.4.3")
    @Override
    public void mine() {
        Direction direction = getDirection();
        int x = getX() + direction.getDx();
        int y = getY() + direction.getDy();
        if (x < 0 || x >= World.getWidth() || y < 0 || y >= World.getHeight()) {
            return;
        }

        Loot objectToMine = WorldUtilities.getLootAt(x, y);
        if (objectToMine == null || isWallInFront()) {
            return;
        }
        Tool tool = getTool();
        Mineable mineable = objectToMine.getMineable();
        if (mineable.onMined(tool)) {
            if (!getInventory().add(mineable)) {
                crash();
            }
            World.getGlobalWorld().removeEntity(objectToMine);
        }
    }

    @StudentImplementationRequired("H05.4.4")
    @Override
    public void handleKeyInput(@Nullable Direction direction, int selection, boolean isPickingGear, boolean isMining, boolean isInfo) {
        if (isMining) {
            mine();
        }
        if (direction != null) {
            while (getDirection() != direction) {
                turnLeft();
            }
            if (isFrontClear()) {
                move();
            }
        }
        if (selection != -1 && selection < getNumberOfEquipments()) {
            useEquipment(selection - 1);
        }
        if (isPickingGear) {
            pickGear();
        }
        if (isInfo) {
            InfoPopup.showInfo(getInventory());
        }
    }
}
