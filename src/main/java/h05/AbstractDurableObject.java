package h05;

public abstract class AbstractDurableObject implements Durable {

    private int durability = 100;

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void reduceDurability(int amount) {
        durability = Math.max(durability - amount, 0);
    }
}
