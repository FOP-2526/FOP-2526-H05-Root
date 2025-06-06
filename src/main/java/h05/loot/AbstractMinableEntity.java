package h05.loot;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    @SolutionOnly
    private final String name;

    @SolutionOnly
    public AbstractMinableEntity(String name) {
        this.name = name;
    }

    @SolutionOnly
    @Override
    public @NotNull String getName() {
        return name;
    }
}
