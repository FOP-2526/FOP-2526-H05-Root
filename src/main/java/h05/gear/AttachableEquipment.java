package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface AttachableEquipment extends Equipment {

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
