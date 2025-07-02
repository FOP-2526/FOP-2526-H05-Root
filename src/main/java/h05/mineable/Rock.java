package h05.mineable;

import h05.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class Rock extends AbstractMinableEntity implements Mineable {

    @DoNotTouch
    public Rock() {
        super("Rock");
    }

    @StudentImplementationRequired
    @Override
    public boolean onMined(@Nullable Tool tool) {
        final double factor;
        if (tool == null) {
            factor = 0.75;
        } else if (tool.getName().equals("Pickaxe")) {
            factor = 3.0;
        } else {
            factor = 1.5;
        }
        reduceDurability(tool == null ? 1 * factor : tool.getMiningPower() * factor);
        return getDurability() <= 0;
    }
}
