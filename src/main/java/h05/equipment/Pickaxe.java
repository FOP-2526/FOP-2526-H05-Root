package h05.equipment;

public class Pickaxe implements Tool {
    private final String name;
    private int durability;

    public Pickaxe() {
        super();
        this.name = "pickaxe";
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
