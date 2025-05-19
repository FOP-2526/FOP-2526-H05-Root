package h05.entity;

import fopbot.Direction;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Equipment;
import h05.gear.Tool;
import h05.loot.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Miner extends Equipable {

    @DoNotTouch
    @NotNull Camera getCamera();

    @DoNotTouch
    @NotNull Battery getBattery();

    @DoNotTouch
    @Nullable Tool getTool();

    @DoNotTouch
    Mineable[] getLootStorage();

    @DoNotTouch
    default boolean isCameraBroken() {
        return getCamera().getCondition() == Equipment.Condition.BROKEN;
    }

    @DoNotTouch
    default boolean isBatteryBroken() {
        return getBattery().getCondition() == Equipment.Condition.BROKEN;
    }

    @DoNotTouch
    void mine();

    @DoNotTouch
    boolean isOnGear();

    @DoNotTouch
    void pickGear();

    @DoNotTouch
    void handleKeyInput(@Nullable Direction direction, int selection, boolean pickGear, boolean mine, boolean info);
}
