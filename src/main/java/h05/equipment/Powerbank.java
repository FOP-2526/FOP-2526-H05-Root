package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

/**
 * A powerbank is a type of equipment that provides additional battery capacity.
 *
 * @author Nhan Huynh, Nico Schnieders
 * @see Battery
 */
@StudentCreationRequired("H5.2.3")
// TODO H5.2.3
public class Powerbank extends AbstractEquipment implements UsableEquipment {

    /**
     * The capacity of this powerbank, which determines how much battery it can provide.
     */
    private final double capacity;

    /**
     * Constructs a new {@link Powerbank} instance with the specified capacity.
     *
     * @param capacity the capacity of the powerbank, must be greater than 0
     */
    public Powerbank(double capacity) {
        super("Powerbank");
        this.capacity = capacity;
    }

    /**
     * Returns the capacity of this powerbank, which determines how much battery it can provide.
     *
     * @return the capacity of this powerbank
     */
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
