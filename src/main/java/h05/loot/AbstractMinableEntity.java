package h05.loot;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * An abstract class representing a mineable entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@SolutionOnly
public abstract class AbstractMinableEntity extends AbstractDurableObject implements Mineable {

    /**
     * The name of the mineable entity.
     */
    @SolutionOnly
    private final String name;

    /**
     * Constructs a new {@link AbstractMinableEntity} instance with the specified name.
     *
     * @param name the name of the mineable entity
     */
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
