package h05;

public abstract class AbstractDurableObject implements Durable {

    private int durability = 100;

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {

        this.durability = durability > 100 ? 0 : Math.max(0, durability);
    }

    @Override
    public void reduceDurability(int amount) {
        durability = Math.max(durability - amount, 0);
    }
}
