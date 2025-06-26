package h05.loot;

import h05.Durable;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents an entity in the game world that can be mined using a {@link Tool}.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public interface Mineable extends Durable {

    /**
     * Returns the name of the mineable entity.
     *
     * @return the name of the mineable entity
     */
    @DoNotTouch
    @NotNull
    String getName();

    /**
     * Returns the current {@link MiningState} of this entity, based on its durability.
     *
     * @return the current mining state based on durability
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
     * Performs the mining action on this entity using the given {@link Tool}, if any.
     *
     * <p>This method applies the mining logic (e.g., reducing durability),
     * and returns whether the entity is now fully mined.
     *
     * @param tool the tool used for mining, or {@code null} if no tool is used
     *
     * @return {@code true} if the entity is fully mined after the action, {@code false} otherwise
     */
    @DoNotTouch
    boolean onMined(@Nullable Tool tool);
}

