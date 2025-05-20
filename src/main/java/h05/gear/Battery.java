package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.concurrent.ThreadLocalRandom;

@DoNotTouch
public class Battery extends AbstractUpgradeableEquipment {

    @DoNotTouch
    public Battery() {
        super("Battery");
    }

    @StudentImplementationRequired
    @Override
    public void attach(@NotNull AttachableEquipment upgrade) {
        super.attach(upgrade);
        if (upgrade.getName().equals("Powerbank")) {
            Powerbank powerbank = EquipmentUtilities.getAsPowerbank(upgrade);
            setDurability(getDurability() + powerbank.getCapacity());
        }
    }

    @StudentImplementationRequired
    @Override
    public void detach(@NotNull AttachableEquipment upgrade) {
        super.detach(upgrade);
        if (upgrade.getName().equals("Powerbank")) {
            Powerbank powerbank = EquipmentUtilities.getAsPowerbank(upgrade);
            setDurability(Math.min(0, getDurability() - powerbank.getCapacity()));
        }
    }

    @StudentImplementationRequired
    @Override
    public void reduceDurability(int amount) {
        super.reduceDurability(amount);
        for (AttachableEquipment upgrade : getUpgrades()) {
            if (upgrade.getName().equals("Powerbank")) {
                Powerbank powerbank = EquipmentUtilities.getAsPowerbank(upgrade);
                if (powerbank.getCondition() == Condition.BROKEN) {
                    continue;
                }
                // TODO: Global random constant for testing
                int reduce = ThreadLocalRandom.current().nextBoolean() ? amount : 0;
                if (powerbank.getDurability() <= reduce) {
                    reduceDurability(powerbank.getCapacity());
                }
                powerbank.reduceDurability(reduce);
            }
        }
    }
}
