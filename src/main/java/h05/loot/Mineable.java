package h05.loot;

import h05.Durable;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Mineable extends Durable {

    @DoNotTouch
    @NotNull String getName();

    @DoNotTouch
    default @NotNull MiningState getState() {
        int durability = getDurability();
        if (durability < 100 && durability > 50) {
            return MiningState.HALF_MINED;
        } else if (durability <= 50 && durability > 0) {
            return MiningState.FULLY_MINED;
        }
        return MiningState.NOT_MINED;
    }

    @DoNotTouch
    boolean onMined(@Nullable Tool tool);
}
