package h05;

import fopbot.Direction;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Miner extends Equipable {

    @DoNotTouch
    void mine();

    @DoNotTouch
    void pickGear();

    @DoNotTouch
    void handleKeyInput(@Nullable Direction direction, int selection, boolean pickGear, boolean mine);
}
