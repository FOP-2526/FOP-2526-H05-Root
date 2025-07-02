package h05.entity;

import fopbot.Direction;
import h05.equipment.Tool;
import h05.base.game.GameSettings;
import h05.base.game.TickBased;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.EquipmentCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Miner extends TickBased, Equipable {

    @DoNotTouch
    @NotNull GameSettings getGameSettings();

    @DoNotTouch
    @NotNull
    Battery getBattery();

    @DoNotTouch
    @NotNull
    Camera getCamera();

    @DoNotTouch
    @Nullable
    Tool getTool();

    @DoNotTouch
    int getX();

    @DoNotTouch
    int getY();


    @DoNotTouch
    @NotNull
    Direction getDirection();

    @DoNotTouch
    default boolean isCameraBroken() {
        return getCamera().getCondition() == EquipmentCondition.BROKEN;
    }

    @DoNotTouch
    default boolean isBatteryBroken() {
        return getBattery().getCondition() == EquipmentCondition.BROKEN;
    }

    @DoNotTouch
    void mine();

    @DoNotTouch
    void pickGear();

    @DoNotTouch
    void handleKeyInput(
        @Nullable Direction direction,
        int selection,
        boolean isPickingGear,
        boolean isMining,
        boolean isInfo
    );
}
