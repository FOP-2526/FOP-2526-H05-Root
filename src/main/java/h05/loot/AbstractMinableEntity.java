package h05.loot;

import h05.AbstractDurableObject;
import h05.Mineable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    @DoNotTouch
    private final String name;

    @DoNotTouch
    private State state;

    @DoNotTouch
    public AbstractMinableEntity(String name) {
        this.name = name;
        this.state = State.NOT_MINED;
    }

    @DoNotTouch
    @Override
    public @NotNull String getName() {
        return name;
    }

    @DoNotTouch
    public @NotNull State getState() {
        return state;
    }

    @DoNotTouch
    public void setState(@NotNull State state) {
        this.state = state;
    }
}
