package h05;

import org.jetbrains.annotations.NotNull;
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
    enum State {

        NOT_MINED,
        HALF_MINED,
        FULLY_MINED
    }
}
