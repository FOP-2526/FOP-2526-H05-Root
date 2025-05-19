package h05;

import fopbot.Direction;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Miner extends Equipable {

    @NotNull Camera getCamera();

    @NotNull Battery getBattery();

    @Nullable Tool getTool();

    default boolean isCameraBroken() {
        return getCamera().getCondition() == Equipment.Condition.BROKEN;
    }

    default boolean isBatteryBroken() {
        return getBattery().getCondition() == Equipment.Condition.BROKEN;
    }

    void mine();

    boolean isOnGear();

    void pickGear();

    void handleKeyInput(@Nullable Direction direction, int selection, boolean pickGear, boolean mine, boolean info);
}
