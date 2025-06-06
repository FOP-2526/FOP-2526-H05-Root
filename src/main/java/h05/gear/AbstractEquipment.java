package h05.gear;

import h05.AbstractDurableObject;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A base skeleton for equipment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {

    /**
     * The name of the equipment.
     */
    @DoNotTouch
    private final @NotNull String name;

    /**
     * Constructs equipment with the specified name.
     *
     * @param name the name of the equipment
     */
    @DoNotTouch
    public AbstractEquipment(@NotNull String name) {
        this.name = name;
    }

    @DoNotTouch
    @Override
    public @NotNull String getName() {
        return name;
    }
}
