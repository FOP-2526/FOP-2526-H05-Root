package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A powerbank is a type of attachable equipment that provides additional battery capacity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Powerbank extends AbstractAttachableEquipment implements AttachableEquipment {

    /**
     * The capacity of the powerbank.
     */
    @DoNotTouch
    private final int capacity;

    /**
     * Constructs a powerbank with the specified capacity.
     *
     * @param capacity the capacity of the powerbank
     */
    @DoNotTouch
    public Powerbank(int capacity) {
        super("Powerbank", "Battery");
        this.capacity = capacity;
    }

    /**
     * Returns the capacity of the powerbank.
     *
     * @return the capacity
     */
    @DoNotTouch
    public int getCapacity() {
        return capacity;
    }
}
