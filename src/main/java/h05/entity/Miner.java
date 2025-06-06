package h05.entity;

import fopbot.Direction;
import h05.game.TickBased;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.EquipmentCondition;
import h05.gear.Tool;
import h05.loot.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A miner is an extension of the FOPBot robot which can equip itself to improve its status and capabilities.
 * Also, it can mine resources.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Miner extends Equipable, TickBased {

    /**
     * Returns the battery of the miner.
     *
     * @return the battery of the miner
     */
    @DoNotTouch
    @NotNull Battery getBattery();

    /**
     * Returns the camera of the miner.
     *
     * @return the camera of the miner
     */
    @DoNotTouch
    @NotNull Camera getCamera();

    /**
     * Returns the tool of the miner if it has one, or {@code null} if it does not.
     *
     * @return the tool of the miner, or {@code null} if it does not have one
     */
    @DoNotTouch
    @Nullable Tool getTool();

    /**
     * Returns the inventory of the miner, which is used to store mineable items.
     *
     * @return the inventory of the miner
     */
    @DoNotTouch
    @NotNull Inventory getInventory();

    /**
     * Returns {@code true} if the camera is broken, {@code false} otherwise.
     *
     * @return {@code true} if the camera is broken, {@code false} otherwise
     */
    @DoNotTouch
    default boolean isCameraBroken() {
        return getCamera().getCondition() == EquipmentCondition.BROKEN;
    }

    /**
     * Returns {@code true} if the battery is broken, {@code false} otherwise.
     *
     * @return {@code true} if the battery is broken, {@code false} otherwise
     */
    @DoNotTouch
    default boolean isBatteryBroken() {
        return getBattery().getCondition() == EquipmentCondition.BROKEN;
    }

    /**
     * Mines the resources at the current direction in front of the miner if its possible.
     */
    @DoNotTouch
    void mine();

    /**
     * Picks up the gear at the current direction in front of the miner if its possible.
     */
    @DoNotTouch
    void pickGear();

    /**
     * Handles the key input for the miner which specifies his action.
     *
     * @param direction     the direction in which the miner is facing, or {@code null} if no direction key is pressed
     * @param selection     the current selection based on the pressed number keys, or -1 if no selection key is pressed
     * @param isPickingGear whether the gear selection key (E) is pressed
     * @param isMining      whether the mining action key (Space) is pressed
     * @param isInfo        whether the info key (I) is pressed
     */
    @DoNotTouch
    void handleKeyInput(
        @Nullable Direction direction,
        int selection,
        boolean isPickingGear,
        boolean isMining,
        boolean isInfo
    );
}
