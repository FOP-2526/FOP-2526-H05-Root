package h05.loot;


import h05.gear.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Tree extends AbstractMinableEntity {

    @DoNotTouch
    public Tree() {
        super("Tree");
    }

    @Override
    public boolean onMined(@Nullable Tool tool) {
        // TODO
        return false;
    }
}
