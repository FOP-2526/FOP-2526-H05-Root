package h05;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A skeleton implementation of the {@link Durable} interface used to simplify the implementation of
 * durable objects in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@SolutionOnly("H5.1")
public class AbstractDurableObject implements Durable {

    /**
     * The current durability of this object, represented as a percentage between 0 and 100.
     */
    private double durability = 100;

    /**
     * Constructs a new {@link AbstractDurableObject} with full durability.
     */
    public AbstractDurableObject() {

    }

    @Override
    public double getDurability() {
        return durability;
    }

    @Override
    public void setDurability(double durability) {
        this.durability = durability > 100 ? 100 : durability < 0 ? 0 : durability;
    }

    @Override
    public void reduceDurability(double amount) {
        double newDurability = durability - amount;
        durability = newDurability < 0 ? 0 : newDurability;
    }
}
