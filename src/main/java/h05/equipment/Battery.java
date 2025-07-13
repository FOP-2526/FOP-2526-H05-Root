package h05.equipment;

import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A battery is a type of equipment that defines the lifetime of an entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public class Battery extends AbstractEquipment {

    /**
     * Constructs a new {@link Battery} instance.
     */
    public Battery() {
        super("Battery");
    }

    /**
     * Increases the durability of this battery by the specified value.
     *
     * @param value the value to increase the durability by
     */
    @StudentImplementationRequired("H5.2.2")
    public void increaseDurability(double value) {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return;
        }
        setDurability(getDurability() + value);
    }

    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public boolean isTool() {
        return false;
    }
}
