package h05.loot;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the mining state of a {@link Mineable} entity.
 *
 * <p>This enum is used to track how far an entity has been mined based on its durability.
 * It distinguishes between entities that are untouched, partially mined, or fully mined.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public enum MiningState {

    /**
     * Indicates that the entity has not been mined yet.
     */
    NOT_MINED,

    /**
     * Indicates that the entity has been partially mined.
     */
    HALF_MINED,

    /**
     * Indicates that the entity has been fully mined.
     */
    FULLY_MINED
}
