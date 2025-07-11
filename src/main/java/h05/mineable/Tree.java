package h05.mineable;

import h05.equipment.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class Tree extends AbstractMinableEntity implements Mineable {

    @DoNotTouch
    public Tree() {
        super("Tree");
    }

    @StudentImplementationRequired
    @Override
    public boolean onMined(@Nullable Tool tool) {
        final double factor;
        if (tool == null) {
            factor = 1; //1
        } else if (tool.getName().equals("Axe")) {
            factor = 4; // 4 * 15 = 60
        } else {
            factor = 1.5; // 1.5 * 30 = 45
        }
        reduceDurability(tool == null ? 1 * factor : tool.getMiningPower() * factor);
        return getDurability() <= 0;
    }
}
