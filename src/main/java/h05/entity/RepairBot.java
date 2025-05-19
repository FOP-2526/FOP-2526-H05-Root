package h05.entity;

import fopbot.Robot;
import fopbot.RobotFamily;
import fopbot.World;
import h05.gear.Equipment;
import h05.WorldUtilities;
import h05.gear.Battery;
import h05.gear.Camera;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Point;

public class RepairBot extends Robot implements Repairer {

    private final int radius;

    public RepairBot(int x, int y, int radius) {
        super(x, y);
        this.setRobotFamily(RobotFamily.SQUARE_RED);
        this.radius = radius;
    }

    @Override
    public int getRadius() {
        return radius;
    }

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
                Miner miner = WorldUtilities.getMinerAtPoint(newX, newY);
                if (miner != null && (miner.isCameraBroken() || miner.isBatteryBroken())) {
                    return new Point(newX, newY);
                }
            }
        }
        return null;
    }

    @Override
    public void repair(@NotNull Point point) {
        setField(point.x, point.y);
        Miner miner = WorldUtilities.getMinerAtPoint(getX(), getY());
        if (miner.isBatteryBroken()) {
            miner.equip(new Battery());
        }
        if (miner.isCameraBroken()) {
            miner.equip(new Camera());
        }
        Equipment[] storage = miner.getEquipmentStorage();
        for (int i = 0; i < miner.getEquipmentCount(); i++) {
            Equipment equipment = storage[i];
            if (equipment.getCondition() == Equipment.Condition.BROKEN) {
                miner.unequip(i);
            }
        }
    }
}
