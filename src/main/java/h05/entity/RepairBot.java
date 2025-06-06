package h05.entity;

import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;
import h05.WorldUtilities;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Equipment;
import h05.gear.EquipmentCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

/**
 * A basic implementation of a  repair bot that can repair miners in the game world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class RepairBot extends Robot implements Repairer {

    /**
     * The radius of the repair bot, defining how far it can reach to repair entities.
     */
    @DoNotTouch
    private final int radius;

    /**
     * Constructs a new {@link RepairBot} instance with the specified position and radius.
     *
     * @param x      the x-coordinate of the repair bot
     * @param y      the y-coordinate of the repair bot
     * @param radius the radius of the repair bot
     */
    @DoNotTouch
    public RepairBot(int x, int y, int radius) {
        super(x, y);
        this.setRobotFamily(RobotFamily.SQUARE_RED);
        this.radius = radius;
    }

    @DoNotTouch
    @Override
    public int getRadius() {
        return radius;
    }

    @StudentImplementationRequired("H05.5")
    @Override
    public @Nullable Point scan() {
        int x = getX();
        int y = getY();
        for (int dx = -radius; dx <= radius; dx++) {
            int newX = x + dx;
            if (newX < 0 || newX >= World.getWidth()) {
                continue;
            }
            for (int dy = -radius; dy <= radius; dy++) {
                int newY = y + dy;
                if (newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                Miner miner = WorldUtilities.getMinerAt(newX, newY);
                if (miner != null && (miner.isCameraBroken() || miner.isBatteryBroken())) {
                    return new Point(newX, newY);
                }
            }
        }
        return null;
    }

    @StudentImplementationRequired("H05.5")
    @Override
    public void repair(@NotNull Point point) {
        setField(point.x, point.y);
        Miner miner = WorldUtilities.getMinerAt(getX(), getY());
        if (miner.isBatteryBroken()) {
            miner.equip(new Battery());
        }
        if (miner.isCameraBroken()) {
            miner.equip(new Camera());
        }
        Equipment[] storage = miner.getEquipments();
        for (int i = 0; i < miner.getNumberOfEquipments(); i++) {
            Equipment equipment = storage[i];
            if (equipment.getCondition() == EquipmentCondition.BROKEN) {
                miner.unequip(i);
            }
        }
    }
}
