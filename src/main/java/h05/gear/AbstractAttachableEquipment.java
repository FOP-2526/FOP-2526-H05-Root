package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public abstract class AbstractAttachableEquipment extends AbstractEquipment implements AttachableEquipment {

    @DoNotTouch
    private final @NotNull String parent;

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
