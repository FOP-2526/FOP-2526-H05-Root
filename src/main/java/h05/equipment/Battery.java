package h05.equipment;

import h05.AttachableEquipment;

import java.util.concurrent.ThreadLocalRandom;

public class Battery extends AbstractUpgradeableEquipment {

    public Battery() {
        super("Battery");
    }

    @Override
    public void attach(AttachableEquipment upgrade) {
        super.attach(upgrade);
        if (upgrade.getName().equals("Powerbank")) {
            Powerbank powerbank = (Powerbank) upgrade;
            setDurability(getDurability() + powerbank.getCapacity());
        }
    }

    @Override
    public void detach(AttachableEquipment upgrade) {
        super.detach(upgrade);
        if (upgrade.getName().equals("Powerbank")) {
            Powerbank powerbank = (Powerbank) upgrade;
            setDurability(Math.min(0, getDurability() - powerbank.getCapacity()));
        }
    }

    @Override
    public void reduceDurability(int amount) {
        super.reduceDurability(amount);
        for (AttachableEquipment upgrade : getUpgrades()) {
            if (upgrade.getName().equals("Powerbank")) {
                // TODO: Do not expose cast
                Powerbank powerbank = (Powerbank) upgrade;
                int reduce = ThreadLocalRandom.current().nextBoolean() ? amount : 0;
                if (powerbank.getDurability() <= reduce) {
                    powerbank.reduceDurability(reduce);
                    reduceDurability(powerbank.getCapacity());
                }
                if (powerbank.getCondition() == Condition.BROKEN) {
                    continue;
                }
                powerbank.reduceDurability(reduce);
            }
        }
    }
}
