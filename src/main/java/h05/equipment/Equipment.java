package h05.equipment;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

public interface Equipment extends Durable {

    @DoNotTouch
    @NotNull
    String getName();

    @StudentImplementationRequired("H5.2.1")
    default @NotNull EquipmentCondition getCondition() {
        return crash();
    }

    boolean isUsable();

    boolean isTool();
}
