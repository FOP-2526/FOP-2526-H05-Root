package h05;

import fopbot.Direction;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface UsableEquipment extends Equipment {

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
