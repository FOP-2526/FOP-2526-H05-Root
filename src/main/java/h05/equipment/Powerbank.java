package h05.equipment;

public class Powerbank extends AbstractAttachableEquipment {

    private final int capacity;

    public Powerbank(int capacity) {
        super("Powerbank");
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
