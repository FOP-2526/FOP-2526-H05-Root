package h05.equipment;

public class Axe implements Tool {
    private final String name;
    private int durability = 100;

    public Axe() {
        super();
        this.name = "axe";
    }

    public String getName() {
        return name;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {
        this.durability = durability;
    }

    public void reduceDurability(int amount) {
        durability -= amount;
    }
}
