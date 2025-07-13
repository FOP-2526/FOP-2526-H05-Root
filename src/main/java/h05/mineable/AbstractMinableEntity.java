package h05.mineable;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A skeleton implementation of the {@link Mineable} interface used to simplify the implementation of
 * minable objects in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@SolutionOnly
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    /**
     * The name of this mineable entity.
     */
    private final String name;

    /**
     * Constructs a new {@link AbstractMinableEntity} with the given name.
     *
     * @param name the name of this mineable entity
     */
    public AbstractMinableEntity(String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
