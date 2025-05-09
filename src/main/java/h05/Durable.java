package h05;

public interface Durable {

    int getDurability();

    void setDurability(int durability);

    void reduceDurability(int amount);
}
