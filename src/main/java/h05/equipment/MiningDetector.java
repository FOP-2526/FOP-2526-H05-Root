package h05.equipment;

import fopbot.Field;
import fopbot.World;
import h05.entitity.Loot;

import java.awt.Color;

public class MiningDetector extends AbstractEquipment {

    public static final int DEFAULT_VISIBILITY_RANGE = 3;

    private final int radius;

    public MiningDetector(int radius) {
        super("Mining Detector");
        this.radius = radius;
    }

    public MiningDetector() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    public void scan(int x, int y) {
        if (getCondition() == Condition.BROKEN) {
            return;
        }
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
                    continue;
                }
                Field field = World.getGlobalWorld().getField(newX, newY);
                if (field.contains(Loot.class)) {
                    field.setFieldColor(Color.GREEN);
                    reduceDurability(5);
                }
            }
        }
        reduceDurability(20);
    }
}
