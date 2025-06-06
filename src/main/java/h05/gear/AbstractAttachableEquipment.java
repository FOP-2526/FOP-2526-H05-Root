package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A base skeleton for attachable equipment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class AbstractAttachableEquipment extends AbstractEquipment implements AttachableEquipment {

    /**
     * The name of the parent equipment to which this attachable equipment can be attached.
     */
    @DoNotTouch
    private final @NotNull String parent;

    /**
     * Constructs attachable equipment with the specified name and parent.
     *
     * @param name   the name of the attachable equipment
     * @param parent the name of the parent equipment to which this attachable equipment can be attached
     */
    @DoNotTouch
    public AbstractAttachableEquipment(@NotNull String name, @NotNull String parent) {
        super(name);
        this.parent = parent;
    }

    @DoNotTouch
    @Override
    public @NotNull String getParent() {
        return parent;
    }
}
