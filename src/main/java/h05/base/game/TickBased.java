package h05.base.game;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface TickBased {

    @DoNotTouch
    default int getUpdateDelay() {
        return 0;
    }
}
