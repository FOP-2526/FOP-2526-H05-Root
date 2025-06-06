package h05.loot;

import h05.Durable;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A mineable entity that can be mined with a tool.
 */
@DoNotTouch
public interface Mineable extends Durable {

    /**
     * Returns the name of the mineable entity.
     *
     * @return the name of the mineable entity
     */
    @DoNotTouch
    @NotNull String getName();

    /**
     * Returns the current mining state of the entity.
     *
     * @return the current mining state
     */
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

    /**
     * Performs the mining action on the entity and returns whether the entity is fully mined.
     *
     * @param tool the tool used for mining, can be {@code null} if no tool is used
     *
     * @return {@code true} if the entity is fully mined, {@code false} otherwise
     */
    @DoNotTouch
    boolean onMined(@Nullable Tool tool);
}
