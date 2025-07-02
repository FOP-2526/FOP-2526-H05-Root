package h05.mineable;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    private final String name;

    public AbstractMinableEntity(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
