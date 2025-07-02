package h05.mineable;

import fopbot.World;
import h05.Tool;
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
            factor = 1;
        } else if (tool.getName().equals("Pickaxe")) {
            factor = 4;
        } else {
            factor = 2;
        }
        reduceDurability(tool == null ? 1 * factor : tool.getMiningPower() * factor);
        return getDurability() <= 0;
    }
}
