package h05.gear;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {

    @DoNotTouch
    private final @NotNull String name;

    @DoNotTouch
    public AbstractEquipment(@NotNull String name) {
        this.name = name;
    }

    @DoNotTouch
    @Override
    public @NotNull String getName() {
        return name;
    }
}
