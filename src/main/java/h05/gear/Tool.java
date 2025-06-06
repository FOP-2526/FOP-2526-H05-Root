package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A tool is a type of equipment used to mine resources.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Tool extends Equipment {

    @DoNotTouch
    default boolean isUpgrade() {
        return false;
    }

    @DoNotTouch
    default boolean isUsable() {
        return false;
    }

    @DoNotTouch
    default boolean isTool() {
        return true;
    }
}
