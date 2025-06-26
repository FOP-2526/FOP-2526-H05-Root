package h05;

import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

/**
 * Represents an entity that has a measurable durability, expressed as a percentage
 * from 0 to 100. The durability value indicates the remaining usability or integrity
 * of the object and can be decreased over time or usage.
 *
 * <p>Typical use cases include tools, items, or objects that degrade over time or usage in simulations or games.
 *
 * <p>Invalid durability values (e.g., negative values or values over 100)
 * are automatically corrected by clamping them to the valid range
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@StudentCreationRequired
public interface Durable {

    /**
     * Returns the current durability of this object.
     * The returned value is guaranteed to be between 0 and 100 (inclusive).
     *
     * @return the current durability percentage of the object
     */
    int getDurability();

    /**
     * Sets the durability of this object to the specified value.
     *
     * @param durability the new durability percentage to set
     */
    void setDurability(int durability);

    /**
     * Decreases the durability of this object by the specified amount.
     * The durability will not fall below 0.
     *
     * @param amount the amount to subtract from the current durability
     */
    void reduceDurability(int amount);
}
