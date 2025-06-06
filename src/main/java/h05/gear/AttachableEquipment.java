package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Attachable equipment is a type of equipment that can be attached to another equipment to enhance its capabilities.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface AttachableEquipment extends Equipment {

    /**
     * Returns the name of the parent equipment to which this attachable equipment can be attached.
     *
     * @return the name of the parent equipment
     */
    @NotNull String getParent();

    @Override
    default boolean isUpgrade() {
        return true;
    }

    @DoNotTouch
    @Override
    default boolean isUsable() {
        return false;
    }

    @DoNotTouch
    @Override
    default boolean isTool() {
        return false;
    }
}
