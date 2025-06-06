package h05.gear;

import h05.game.GameConstants;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

@DoNotTouch
public class Battery extends AbstractUpgradeableEquipment {

    @DoNotTouch
    public Battery() {
        super("Battery");
    }

    @StudentImplementationRequired("H05.2.2")
    @Override
    public void attach(@NotNull AttachableEquipment upgrade) {
        super.attach(upgrade);
        if (upgrade.getName().equals("Powerbank")) {
            Powerbank powerbank = EquipmentUtilities.getAsPowerbank(upgrade);
            setDurability(getDurability() + powerbank.getCapacity());
        }
    }

    @StudentImplementationRequired("H05.2.2")
    @Override
    public void detach(@NotNull AttachableEquipment upgrade) {
        super.detach(upgrade);
        if (upgrade.getName().equals("Powerbank")) {
            Powerbank powerbank = EquipmentUtilities.getAsPowerbank(upgrade);
            setDurability(Math.min(0, getDurability() - powerbank.getCapacity()));
        }
    }

    @StudentImplementationRequired("H05.2.2")
    @Override
    public void reduceDurability(int amount) {
        super.reduceDurability(amount);
        for (AttachableEquipment upgrade : getUpgrades()) {
            if (upgrade.getName().equals("Powerbank")) {
                Powerbank powerbank = EquipmentUtilities.getAsPowerbank(upgrade);
                if (powerbank.getCondition() == EquipmentCondition.BROKEN) {
                    continue;
                }
                int reduce = GameConstants.RANDOM_GENERATOR.nextBoolean() ? amount : 0;
                if (powerbank.getDurability() <= reduce) {
                    reduceDurability(powerbank.getCapacity());
                }
                powerbank.reduceDurability(reduce);
            }
        }
    }
}
