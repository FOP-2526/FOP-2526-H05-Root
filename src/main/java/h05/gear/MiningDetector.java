package h05.gear;

import fopbot.Direction;
import fopbot.Field;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Loot;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Usable equipment that detects mining resources in the world in a specified radius.
 */
@DoNotTouch
public class MiningDetector extends AbstractEquipment implements UsableEquipment {

    /**
     * The default visibility range for the mining detector.
     */
    @DoNotTouch
    public static final int DEFAULT_VISIBILITY_RANGE = 3;

    /**
     * The radius within which the mining detector can detect resources.
     */
    @DoNotTouch
    private final int radius;

    /**
     * Constructs a mining detector with the specified radius.
     *
     * @param radius the radius within which the mining detector can detect resources
     */
    @DoNotTouch
    public MiningDetector(int radius) {
        super("MiningDetector");
        this.radius = radius;
    }

    /**
     * Constructs a mining detector with the default visibility range.
     */
    @DoNotTouch
    public MiningDetector() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    @DoNotTouch
    @Override
    public void use(int x, int y, @NotNull Direction direction) {
        if (getCondition() == EquipmentCondition.BROKEN) {
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
