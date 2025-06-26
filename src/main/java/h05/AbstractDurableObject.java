package h05;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A skeletal implementation of the {@link Durable} interface, intended to simplify
 * the creation of durable objects within the game.
 *
 * <p>Subclasses can focus on game-specific behavior without needing to reimplement
 * durability management.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@SolutionOnly
public class AbstractDurableObject implements Durable {

    /**
     * The current durability of the object in percent. Guaranteed to be in the range [0, 100].
     */
    private int durability = 100;

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {
        this.durability = durability > 100 ? 100 : durability < 0 ? 0 : durability;
    }

    @Override
    public void reduceDurability(int amount) {
        int newDurability = durability - amount;
        durability = newDurability < 0 ? 0 : newDurability;
    }
}
