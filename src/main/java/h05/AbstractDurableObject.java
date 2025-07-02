package h05;

import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public class AbstractDurableObject implements Durable {

    private double durability = 100;

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
