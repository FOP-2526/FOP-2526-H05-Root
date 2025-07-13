package h05.mineable;

import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public interface Mineable extends Durable {

    @DoNotTouch
    @NotNull
    String getName();

    @NotNull
    @DoNotTouch
    default MiningProgress getProgress() {
        final double durability = getDurability();
        if (durability < 100 && durability > 50) {
            return MiningProgress.IN_PROGRESS;
        } else if (durability <= 50 && durability > 0) {
            return MiningProgress.COMPLETED;
        }
        return MiningProgress.UNSTARTED;
    }

    @DoNotTouch
    boolean onMined(@Nullable Tool tool);
}
