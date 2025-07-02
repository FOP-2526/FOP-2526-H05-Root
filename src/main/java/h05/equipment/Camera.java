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

    /**
     * Constructs a camera with the default visibility range.
     */
    @DoNotTouch
    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    @StudentImplementationRequired
    public int getVisibilityRange() {
        if (getCondition() == EquipmentCondition.DAMAGED) {
            return 0;
        }
        return visibilityRange;
    }

    @StudentImplementationRequired
    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = visibilityRange < 1 ? 1 : visibilityRange;
    }
}
