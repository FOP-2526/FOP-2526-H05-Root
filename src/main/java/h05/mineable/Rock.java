package h05.mineable;

import h05.equipment.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a rock that can be mined using tools.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Rock extends AbstractMinableEntity implements Mineable {

    /**
     * Constructs a new {@link Rock} instance.
     */
    @DoNotTouch
    public Rock() {
        super("Rock");
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        // TODO H5.3
        final double factor;
        if (tool == null) {
            factor = 5; // 5 * 1 = 5
        } else if (tool.getName().equals("Pickaxe")) {
            factor = 2; // 2 * 15 = 30
        } else {
            factor = 1.5; // 1.5 * 15 = 22.5
        }

        reduceDurability(tool == null ? 1 * factor : tool.getMiningPower() * factor);
        return getDurability() == 0;
    }
}
