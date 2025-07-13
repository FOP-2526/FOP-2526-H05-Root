package h05.equipment;

import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

/**
 * Represents a pickaxe that can be used for mining.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@StudentCreationRequired("H5.2.4")
public class Pickaxe extends AbstractEquipment implements Tool {

    /**
     * Constructs a new {@link Pickaxe} instance.
     */
    public Pickaxe() {
        super("Pickaxe");
    }

    @Override
    public double getMiningPower() {
        return 15;
    }
}
