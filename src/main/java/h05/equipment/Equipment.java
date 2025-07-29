package h05.equipment;

import h05.Durable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Equipment is an object that enhances the status or abilities of the entity it is attached to.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public interface Equipment extends Durable {

    /**
     * Returns the name of this equipment.
     *
     * @return the name of this equipment
     */
    @DoNotTouch
    @NotNull
    String getName();

    /**
     * Returns the condition of this equipment.
     *
     * @return the condition of this equipment
     */
    @StudentImplementationRequired("H5.2.1")
    default @NotNull EquipmentCondition getCondition() {
        // TODO H5.2.1
        final double durability = getDurability();
        if (durability > 80) {
            return EquipmentCondition.NEW;
        } else if (durability > 40) {
            return EquipmentCondition.USED;
        } else if (durability > 0) {
            return EquipmentCondition.DAMAGED;
        }
        return EquipmentCondition.BROKEN;
    }

    /**
     * Returns {@code true} if this equipment is usable, otherwise {@code false}.
     *
     * @return {@code true} if this equipment is usable, otherwise {@code false}
     * @see UsableEquipment
     */
    boolean isUsable();

    /**
     * Returns {@code true} if this equipment is a tool, otherwise {@code false}.
     *
     * @return {@code true} if this equipment is a tool, otherwise {@code false}
     * @see Tool
     */
    boolean isTool();
}
