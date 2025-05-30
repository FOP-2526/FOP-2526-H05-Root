package h05.gear;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {


    private final String name;

    public AbstractEquipment(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
