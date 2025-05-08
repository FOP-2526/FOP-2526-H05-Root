package h05.equipment;

import h05.AbstractDurableObject;
import h05.Equipment;

public class AbstractEquipment extends AbstractDurableObject implements Equipment {

    private final String name;

    public AbstractEquipment(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
