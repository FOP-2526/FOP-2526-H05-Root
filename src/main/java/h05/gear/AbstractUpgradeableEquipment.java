package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Arrays;

/**
 * A base skeleton implementation of upgradeable equipment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class AbstractUpgradeableEquipment extends AbstractEquipment implements UpgradeableEquipment {

    /**
     * The default capacity of the upgradeable equipment.
     */
    @DoNotTouch
    private static final int DEFAULT_CAPACITY = 5;

    /**
     * The array of upgrades that can be attached to this equipment.
     * The size of the array is defined by the capacity of the equipment.
     */
    @DoNotTouch
    private final AttachableEquipment[] upgrades;

    /**
     * The number of upgrades that have been attached to this equipment.
     * This is used to keep track of how many upgrades are currently attached.
     */
    @SolutionOnly
    private int numberOfUpgrades;

    /**
     * Constructs an upgradeable equipment with the specified name and capacity.
     *
     * @param name     the name of the equipment
     * @param capacity the maximum number of upgrades that can be attached to this equipment
     */
    @DoNotTouch
    public AbstractUpgradeableEquipment(@NotNull String name, int capacity) {
        super(name);
        this.upgrades = new AttachableEquipment[capacity];
    }

    /**
     * Constructs an upgradeable equipment with the specified name and a default capacity.
     *
     * @param name the name of the equipment
     */
    @DoNotTouch
    public AbstractUpgradeableEquipment(@NotNull String name) {
        this(name, DEFAULT_CAPACITY);
    }

    @StudentImplementationRequired("H05.2.1")
    @Override
    public AttachableEquipment[] getUpgrades() {
        return Arrays.copyOf(upgrades, numberOfUpgrades);
    }

    @StudentImplementationRequired("H05.2.1")
    @Override
    public int getNumberOfUpgrades() {
        return numberOfUpgrades;
    }

    @StudentImplementationRequired("H05.2.1")
    @Override
    public void attach(@NotNull AttachableEquipment upgrade) {
        if (numberOfUpgrades == upgrades.length) {
            return;
        }
        upgrades[numberOfUpgrades++] = upgrade;
    }

    @StudentImplementationRequired("H05.2.1")
    @Override
    public void detach(@NotNull AttachableEquipment upgrade) {
        int i = 0;
        for (; i < numberOfUpgrades; i++) {
            if (upgrades[i] == upgrade) {
                upgrades[i] = null;
                break;
            }
        }
        for (; i < numberOfUpgrades - 1; i++) {
            upgrades[i] = upgrades[i + 1];
        }
        numberOfUpgrades--;
    }
}
