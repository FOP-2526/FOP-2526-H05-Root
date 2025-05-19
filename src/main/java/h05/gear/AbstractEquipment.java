package h05.gear;

import h05.AbstractDurableObject;
import h05.Equipment;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {

    private final String name;

    public AbstractEquipment(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
