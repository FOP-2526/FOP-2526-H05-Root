package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Arrays;

@DoNotTouch
public abstract class AbstractUpgradeableEquipment extends AbstractEquipment implements UpgradeableEquipment {

    @DoNotTouch
    private static final int DEFAULT_CAPACITY = 5;

    @DoNotTouch
    private final AttachableEquipment[] upgrades;

    @SolutionOnly
    private int numberOfUpgrades;

    @DoNotTouch
    public AbstractUpgradeableEquipment(@NotNull String name, int capacity) {
        super(name);
        this.upgrades = new AttachableEquipment[capacity];
    }

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
