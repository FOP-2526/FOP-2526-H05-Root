package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Powerbank extends AbstractEquipment implements AttachableEquipment {

    @DoNotTouch
    private final int capacity;

    @DoNotTouch
    public Powerbank(int capacity) {
        super("Powerbank");
        this.capacity = capacity;
    }

    @DoNotTouch
    public int getCapacity() {
        return capacity;
    }
}
