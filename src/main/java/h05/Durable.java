package h05;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents an object that has durability, which can be reduced.
 * The durability is an integer value between 0 and 100 that represents the percentage of durability remaining.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Durable {

    /**
     * Returns the current durability of the object.
     *
     * @return the current durability
     */
    @DoNotTouch
    int getDurability();

    /**
     * Sets the durability of the object to a specified value.
     *
     * @param durability the new durability value
     */
    @DoNotTouch
    void setDurability(int durability);

    /**
     * Reduces the durability of the object by a specified amount.
     * The amount must be a positive integer.
     *
     * @param amount the amount to reduce the durability by
     */
    @DoNotTouch
    void reduceDurability(int amount);
}
