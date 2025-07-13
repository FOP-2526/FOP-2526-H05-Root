package h05.equipment;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

/**
 * A skeleton implementation of the {@link AbstractEquipment} interface used to simplify the implementation of
 * equipment objects in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@SolutionOnly
public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {

    /**
     * The name of this equipment.
     */
    private final @NotNull String name;

    /**
     * Constructs a new {@link AbstractEquipment} with the given name.
     *
     * @param name the name of this equipment
     */
    public AbstractEquipment(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
