package h05;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

@DoNotTouch
public abstract class AbstractDurableObject implements Durable {

    @SolutionOnly
    private int durability = 100;

    @StudentImplementationRequired
    @Override
    public int getDurability() {
        return durability;
    }

    @StudentImplementationRequired
    @Override
    public void setDurability(int durability) {
        this.durability = durability > 100 ? 0 : Math.max(0, durability);
    }

    @StudentImplementationRequired
    @Override
    public void reduceDurability(int amount) {
        durability = Math.max(durability - amount, 0);
    }
}
