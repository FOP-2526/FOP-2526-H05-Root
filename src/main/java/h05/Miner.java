package h05;

import fopbot.Direction;
import org.jetbrains.annotations.Nullable;

public interface Miner extends Equipable {

    void handleKeyInput(@Nullable Direction direction, int selection, boolean pickGear, boolean mine);

    void mine();

    boolean isOnGear();

    void pickGear();
}
