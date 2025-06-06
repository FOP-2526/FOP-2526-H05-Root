package h05.loot;


import fopbot.World;
import h05.gear.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class Rock extends AbstractMinableEntity implements Mineable {

    // TODO Annotation fix @StudentImplementationRequired("H05.3")
    public Rock() {
        super("Rock");
    }

    @StudentImplementationRequired("H05.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        int reduce;
        if (tool == null) {
            reduce = 2;
        } else if (tool.getName().equals("Pickaxe")) {
            reduce = 12;
        } else {
            // Pickaxe
            reduce = 4;
        }
        reduceDurability(reduce);
        World.getGlobalWorld().getGuiPanel().updateGui();
        return getDurability() <= 0;
    }
}
