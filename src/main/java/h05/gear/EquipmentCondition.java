package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents the condition of equipment.
 */
@DoNotTouch
public enum EquipmentCondition {
    /**
     * The equipment is in new condition.
     */
    NEW,

    /**
     * The equipment is in used condition.
     */
    USED,

    /**
     * The equipment is in damaged condition.
     */
    DAMAGED,

    /**
     * The equipment is broken and cannot be used.
     */
    BROKEN
}
