package h05;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Durable {

    @DoNotTouch
    int getDurability();

    @DoNotTouch
    void setDurability(int durability);

    @DoNotTouch
    void reduceDurability(int amount);
}
