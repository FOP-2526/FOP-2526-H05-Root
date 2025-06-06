package h05.loot;


import fopbot.World;
import h05.gear.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Represents a rock which can be mined by a tool.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Rock extends AbstractMinableEntity implements Mineable {

    // TODO Annotation fix @StudentImplementationRequired("H05.3")

    /**
     * Constructs a new {@link Rock} instance.
     */
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
