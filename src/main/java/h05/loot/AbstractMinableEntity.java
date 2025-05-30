package h05.loot;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    @SolutionOnly
    private final String name;

    @SolutionOnly
    private MiningState state;

    @SolutionOnly
    public AbstractMinableEntity(String name) {
        this.name = name;
        this.state = MiningState.NOT_MINED;
    }

    @SolutionOnly
    @Override
    public @NotNull String getName() {
        return name;
    }

    @SolutionOnly
    public @NotNull MiningState getState() {
        return state;
    }

    @SolutionOnly
    public void setState(@NotNull MiningState state) {
        this.state = state;
    }
}
