package h05.equipment;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

public class Camera implements Equipment {

    @DoNotTouch
    public static final int DEFAULT_VISIBILITY_RANGE = 1;

    @DoNotTouch
    private int visibilityRange;

    public Camera(int visibilityRange) {

    }

    @DoNotTouch
    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    @StudentImplementationRequired("H5.2.2")
    public int getVisibilityRange() {
        return crash();
    }

    @StudentImplementationRequired("H5.2.2")
    public void setVisibilityRange(int visibilityRange) {
        crash();
    }

    @Override
    @StudentImplementationRequired
    public @NotNull String getName() {
        return crash();
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
