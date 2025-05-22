package h05.loot;


import fopbot.World;
import h05.gear.Tool;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Rock extends AbstractMinableEntity {

    @DoNotTouch
    public Rock() {
        super("Rock");
    }

    @Override
    public boolean onMined(@Nullable Tool tool) {
        int reduce;
        if (tool == null || !tool.getName().equals("Pickaxe")) {
            reduce = 5;
        } else reduce = 10;
        reduceDurability(reduce);
        updateState();
        return getDurability() <= 0;
    }

    private void updateState() {
        int durability = getDurability();
        if (durability < 100 && durability > 50) {
            this.setState(Mineable.State.HALF_MINED);
        } else if (durability <= 50 && durability > 0) {
            this.setState(Mineable.State.FULLY_MINED);
        }
        World.getGlobalWorld().getGuiPanel().repaint();
    }
}
