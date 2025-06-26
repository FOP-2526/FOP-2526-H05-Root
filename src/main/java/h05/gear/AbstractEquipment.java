package h05.gear;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A skeletal base implementation of the {@link Equipment} interface.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@SolutionOnly
public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {

    /**
     * The name of the equipment.
     */
    private final @NotNull String name;

    /**
     * Constructs a new equipment instance with the given name.
     *
     * @param name the name of the equipment; must not be {@code null}
     */
    public AbstractEquipment(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}

