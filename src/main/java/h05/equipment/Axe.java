package h05.equipment;

public class Axe implements Tool {
    private final String name;
    private int durability;

    public Axe() {
        super();
        this.name = "axe";
        this.durability = 100;
    }

    public String getName() {
        return name;
    }

    public int getDurability() {
        return durability;
    }

    public void reduceDurability(int amount) {
        durability -= amount;
    }
}
