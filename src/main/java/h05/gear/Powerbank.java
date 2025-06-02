package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Powerbank extends AbstractAttachableEquipment implements AttachableEquipment {

    @DoNotTouch
    private final int capacity;

    @DoNotTouch
    public Powerbank(int capacity) {
        super("Powerbank", "Battery");
        this.capacity = capacity;
    }

    @DoNotTouch
    public int getCapacity() {
        return capacity;
    }
}
