package h05.entity;

import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;
import h05.WorldUtilities;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Equipment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.Point;

@DoNotTouch
public class RepairBot extends Robot implements Repairer {

    @DoNotTouch
    private final int radius;

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


    @StudentImplementationRequired
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

    @StudentImplementationRequired
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
            if (equipment.getCondition() == Equipment.Condition.BROKEN) {
                miner.unequip(i);
            }
        }
    }
}
