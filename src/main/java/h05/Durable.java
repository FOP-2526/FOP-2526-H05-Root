package h05;

public interface Durable {

    int getDurability();

    void setDurability(int durability);

    void increaseDurability(int amount);

    void reduceDurability(int amount);
}
