package h05.mineable;

import h05.equipment.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a tree that can be mined using tools.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Tree extends AbstractMinableEntity implements Mineable {

    /**
     * Constructs a new {@link Tree} instance.
     */
    @DoNotTouch
    public Tree() {
        super("Tree");
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        // TODO H5.3
        final double factor;
        if (tool == null) {
            factor = 7.5; // 3 * 1 = 7.5
        } else if (tool.getName().equals("Axe")) {
            factor = 4; // 4 * 5 = 20
        } else {
            factor = 3; // 3 * 5 = 15
        }
        reduceDurability(tool == null ? 1 * factor : tool.getMiningPower() * factor);
        return getDurability() <= 0;
    }
}
