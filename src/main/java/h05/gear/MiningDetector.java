package h05.equipment;

import fopbot.Direction;
import fopbot.Field;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;

public class MiningDetector extends AbstractUsableEquipment {

    public static final int DEFAULT_VISIBILITY_RANGE = 3;

    private final int radius;

    public MiningDetector(int radius) {
        super("Mining Detector");
        this.radius = radius;
    }

    public MiningDetector() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    @Override
    public void use(int x, int y, Direction direction) {
        if (getCondition() == Condition.BROKEN) {
            return;
        }
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
                Field field = World.getGlobalWorld().getField(newX, newY);
                if (field.contains(Loot.class)) {
                    reduceDurability(5);
                    if (field.contains(Fog.class)) {
                        reduceDurability(20);
                        field.removeEntity(Fog.class);
                        World.getGlobalWorld().getGuiPanel().updateGui();
                        return;
                    }
                }
            }
        }

    }
}
