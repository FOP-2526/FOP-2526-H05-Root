package h05.entity;

import fopbot.Direction;
import h05.game.GameSettings;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.EquipmentCondition;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A miner is an extension of the FOPBot robot that can equip various tools and devices
 * to enhance its capabilities and status.
 *
 * <p>In addition to movement and interaction, a miner can:
 * <ul>
 *     <li>Equip itself with tools and equipments to enhance its capabilites and status</li>
 *     <li>Actively use tools to perform actions</li>
 *     <li>Mine resources in the game world</li>
 * </ul>
 *
 * <p>This interface builds on the standard FOPBot functionality by adding modular equipment support
 * and mining-specific behavior.
 *
 * @author Nhan Huynh, Nico Schnieders
 */

public interface Miner {

    /**
     * Provides access to the game settings associated with this miner.
     *
     * @return the current game settings
     */
    @NotNull GameSettings getGameSettings();

    /**
     * Returns the battery attached to this miner.
     *
     * @return the non-null battery of this miner
     */
    @DoNotTouch
    @NotNull
    Battery getBattery();

    /**
     * Returns the camera attached to this miner.
     *
     * @return the non-null camera of this miner
     */
    @DoNotTouch
    @NotNull
    Camera getCamera();

    /**
     * Returns the currently attached tool, or {@code null} if no tool is equipped.
     *
     * @return the currently equipped {@link Tool}, or {@code null} if none
     */
    @DoNotTouch
    @Nullable
    Tool getTool();

    /**
     * Returns the current x-coordinate of the miner's position.
     *
     * @return the x-coordinate
     */
    int getX();

    /**
     * Returns the current y-coordinate of the miner's position.
     *
     * @return the y-coordinate
     */
    int getY();

    /**
     * Returns the direction the miner is currently facing.
     *
     * @return the non-null facing {@link Direction} of the miner
     */
    @NotNull
    Direction getDirection();

    /**
     * Checks whether the camera is broken (i.e., condition is {@link EquipmentCondition#BROKEN}).
     *
     * @return {@code true} if the camera is broken, {@code false} otherwise
     */
    @DoNotTouch
    default boolean isCameraBroken() {
        return getCamera().getCondition() == EquipmentCondition.BROKEN;
    }

    /**
     * Checks whether the battery is broken (i.e., condition is {@link EquipmentCondition#BROKEN}).
     *
     * @return {@code true} if the battery is broken, {@code false} otherwise
     */
    @DoNotTouch
    default boolean isBatteryBroken() {
        return getBattery().getCondition() == EquipmentCondition.BROKEN;
    }

    /**
     * Mines the resources at the current direction in front of the miner if it is possible.
     */
    @DoNotTouch
    void mine();

    /**
     * Picks up the gear at the current direction in front of the miner if it is possible.
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
