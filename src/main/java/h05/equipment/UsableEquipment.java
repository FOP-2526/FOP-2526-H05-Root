package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Usable equipment is a type of equipment that can be used by a miner to perform actions or gain benefits.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public interface UsableEquipment extends Equipment {

    /**
     * Uses this equipment with the given miner.
     *
     * @param miner the miner using this equipment
     */
    @DoNotTouch
    void use(@NotNull Miner miner);

    @Override
    default boolean isUsable() {
        return true;
    }

    @Override
    default boolean isTool() {
        return false;
    }
}
