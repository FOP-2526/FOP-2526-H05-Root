package h05.loot;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    @SolutionOnly
    private final String name;

    @SolutionOnly
    private State state;

    @SolutionOnly
    public AbstractMinableEntity(String name) {
        this.name = name;
        this.state = State.NOT_MINED;
    }

    @SolutionOnly
    @Override
    public @NotNull String getName() {
        return name;
    }

    @SolutionOnly
    public @NotNull State getState() {
        return state;
    }

    @SolutionOnly
    public void setState(@NotNull State state) {
        this.state = state;
    }
}
