package h05.gear;

import fopbot.Direction;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Usable equipment is a type of equipment that can be used on a specific position and direction to perform an action.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface UsableEquipment extends Equipment {

    /**
     * Uses the equipment at the specified position and direction.
     *
     * @param x         the x-coordinate of the position where the equipment is used
     * @param y         the y-coordinate of the position where the equipment is used
     * @param direction the direction in which the equipment is used
     */
    @DoNotTouch
    void use(int x, int y, @NotNull Direction direction);

    @Override
    default boolean isUpgrade() {
        return false;
    }

    @Override
    default boolean isUsable() {
        return true;
    }

    @Override
    default boolean isTool() {
        return false;
    }
}
