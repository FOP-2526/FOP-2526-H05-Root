package h05.gear;

import h05.Durable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An equipment which can enhance the entity status and capabilities whose it is attached to.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public interface Equipment extends Durable {

    /**
     * Returns the name of the equipment.
     *
     * @return the name of the equipment
     */
    @DoNotTouch
    @NotNull String getName();

    /**
     * Returns the condition of the equipment.
     *
     * @return the condition of the equipment
     */
    @DoNotTouch
    default @NotNull EquipmentCondition getCondition() {
        int durability = getDurability();
        if (durability > 80) {
            return EquipmentCondition.NEW;
        } else if (durability > 40) {
            return EquipmentCondition.USED;
        } else if (durability > 0) {
            return EquipmentCondition.DAMAGED;
        }
        return EquipmentCondition.BROKEN;
    }
}
