package h05;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A skeleton implementation of the {@link Durable} interface used to simplify the implementation of
 * durable objects in the game.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class AbstractDurableObject implements Durable {

    /**
     * The current durability of the object in percent.
     */
    @SolutionOnly
    private int durability = 100;

    @StudentImplementationRequired("H05.1")
    @Override
    public int getDurability() {
        return durability;
    }

    @StudentImplementationRequired("H05.1")
    @Override
    public void setDurability(int durability) {
        this.durability = durability > 100 ? 100 : durability < 0 ? 0 : durability;
    }

    @StudentImplementationRequired("H05.1")
    @Override
    public void reduceDurability(int amount) {
        int newDurability = durability - amount;
        durability = newDurability < 0 ? 0 : newDurability;
    }
}
