package h05.loot;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the mining state of an entity.
 * This enum is used to track whether an entity has been mined partially or fully.
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
