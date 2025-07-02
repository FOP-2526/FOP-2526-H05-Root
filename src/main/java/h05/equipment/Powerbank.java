package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired
public class Powerbank extends AbstractEquipment implements UsableEquipment {

    private final double capacity;

    @DoNotTouch
    public Powerbank(double capacity) {
        super("Powerbank");
        this.capacity = capacity;
    }

    public double getCapacity() {
        return capacity;
    }

    @Override
    public void use(@NotNull Miner miner) {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return;
        }
        miner.getBattery().increaseDurability(capacity / 2);
        reduceDurability(50);
    }
}
