package h05.entity;

import fopbot.Direction;
import h05.game.TickBased;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Equipment;
import h05.gear.Tool;
import h05.loot.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Miner extends Equipable, TickBased {

    @DoNotTouch
    @NotNull Battery getBattery();

    @DoNotTouch
    @NotNull Camera getCamera();

    @DoNotTouch
    @Nullable Tool getTool();

    @DoNotTouch
    @NotNull Inventory getInventory();

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
    void pickGear();

    @DoNotTouch
    void handleKeyInput(@Nullable Direction direction, int selection, boolean pickGear, boolean mine, boolean info);
}
