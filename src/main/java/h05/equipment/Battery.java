package h05.equipment;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

public class Battery implements Equipment {

    public Battery() {
    }

    @StudentImplementationRequired("H5.2.2")
    public void increaseDurability(double value) {
        crash();
    }

    @Override
    @StudentImplementationRequired
    public @NotNull String getName() {
        return crash();
    }

    @Override
    public boolean isUsable() {
        return false;
    }

    @Override
    public boolean isTool() {
        return false;
    }
}
