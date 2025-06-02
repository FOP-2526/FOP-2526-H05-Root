package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class AbstractAttachableEquipment extends AbstractEquipment implements AttachableEquipment {

    @DoNotTouch
    private final String parent;

    @DoNotTouch
    public AbstractAttachableEquipment(@NotNull String name, String parent) {
        super(name);
        this.parent = parent;
    }

    @DoNotTouch
    @Override
    public String getParent() {
        return parent;
    }
}
