package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the condition or state of a piece of equipment.
 *
 * <p>This enum is typically used to reflect the usability and integrity of an item
 * such as tools, weapons, or armor in a game context. The condition may influence
 * effectiveness, durability, or availability of the equipment.
 *
 * <p>Typical transitions may occur through usage, damage, or repair mechanisms.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public enum EquipmentCondition {

    /**
     * The equipment is in perfect condition and has not been used.
     */
    NEW,

    /**
     * The equipment has been used but is still functional and effective.
     */
    USED,

    /**
     * The equipment is damaged and may be less effective or partially functional.
     */
    DAMAGED,

    /**
     * The equipment is broken and cannot be used until repaired.
     */
    BROKEN
}

