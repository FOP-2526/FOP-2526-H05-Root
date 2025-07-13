package h05.mineable;

import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

public class Rock implements Mineable {

    public Rock() {
    }

    @Override
    @StudentImplementationRequired
    public @NotNull String getName() {
        return crash();
    }

    @StudentImplementationRequired("H5.3")
    @Override
    public boolean onMined(@Nullable Tool tool) {
        return crash();
    }
}
