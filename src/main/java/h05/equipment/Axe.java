package h05.equipment;

import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

/**
 * Represents an axe that can be used for mining.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@StudentCreationRequired("H5.2.4")
public class Axe extends AbstractEquipment implements Tool {

    /**
     * Constructs a new {@link Axe} instance.
     */
    public Axe() {
        super("Axe");
    }

    @Override
    public double getMiningPower() {
        return 5;
    }
}
