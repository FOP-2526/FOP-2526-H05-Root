package h05.gear;

import h05.Durable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Equipment extends Durable {

    @DoNotTouch
    @NotNull String getName();

    @DoNotTouch
    default @NotNull Condition getCondition() {
        int durability = getDurability();
        if (durability > 80) {
            return Condition.NEW;
        } else if (durability > 40) {
            return Condition.USED;
        } else if (durability > 0) {
            return Condition.DAMAGED;
        }
        return Condition.BROKEN;
    }

    @DoNotTouch
    boolean isUpgrade();

    @DoNotTouch
    boolean isUsable();

    @DoNotTouch
    boolean isTool();

    @DoNotTouch
    enum Condition {
        NEW,
        USED,
        DAMAGED,
        BROKEN
    }
}
