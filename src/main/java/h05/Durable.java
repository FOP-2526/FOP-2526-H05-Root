package h05;

public interface Durable {

    int getDurability();

    void reduceDurability(int amount);
}
