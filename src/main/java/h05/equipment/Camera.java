package h05.equipment;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class Camera extends AbstractEquipment {

    @DoNotTouch
    public static final int DEFAULT_VISIBILITY_RANGE = 1;

    @DoNotTouch
    private int visibilityRange;

    @DoNotTouch
    public Camera(int visibilityRange) {
        super("Camera");
        this.visibilityRange = visibilityRange;
    }

    @DoNotTouch
    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    @StudentImplementationRequired("H5.2.2")
    public int getVisibilityRange() {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return 0;
        }
        if (getCondition() == EquipmentCondition.DAMAGED) {
            return visibilityRange / 2;
        }
        return visibilityRange;
    }

    @StudentImplementationRequired("H5.2.2")
    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = visibilityRange < 1 ? 1 : visibilityRange;
    }

    @Override
    @DoNotTouch
    public boolean isUsable() {
        return false;
    }

    @Override
    @DoNotTouch
    public boolean isTool() {
        return false;
    }
}
