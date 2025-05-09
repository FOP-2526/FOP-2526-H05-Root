package h05.equipment;

import h05.AttachableEquipment;
import h05.UpgradeableEquipment;

import java.util.Arrays;

public abstract class AbstractUpgradeableEquipment extends AbstractEquipment implements UpgradeableEquipment {

    private static final int DEFAULT_CAPACITY = 5;

    private final AttachableEquipment[] upgrades;

    private int numberOfUpgrades;

    public AbstractUpgradeableEquipment(String name, int capacity) {
        super(name);
        this.upgrades = new AttachableEquipment[capacity];
    }

    public AbstractUpgradeableEquipment(String name) {
        this(name, DEFAULT_CAPACITY);
    }

    @Override
    public AttachableEquipment[] getUpgrades() {
        return Arrays.copyOf(upgrades, numberOfUpgrades);
    }

    @Override
    public int getNumberOfUpgrades() {
        return numberOfUpgrades;
    }

    @Override
    public boolean isUpgrade() {
        return false;
    }

    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public void attach(AttachableEquipment upgrade) {
        upgrades[numberOfUpgrades++] = upgrade;
    }

    @Override
    public void detach(AttachableEquipment upgrade) {
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
