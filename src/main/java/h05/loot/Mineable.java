package h05.loot;

import h05.Durable;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Mineable extends Durable {

    @DoNotTouch
    @NotNull String getName();

    @DoNotTouch
    @NotNull State getState();

    @DoNotTouch
    void setState(@NotNull State state);

    @DoNotTouch
    boolean onMined(@Nullable Tool tool);

    @DoNotTouch
    enum State {

        NOT_MINED,
        HALF_MINED,
        FULLY_MINED
    }
}
