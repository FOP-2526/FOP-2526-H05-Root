package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import static org.tudalgo.algoutils.student.Student.crash;

public class WallBreaker implements UsableEquipment {


    public WallBreaker() {
    }

    @StudentImplementationRequired("H5.2.5")
    @Override
    public void use(@NotNull Miner miner) {
        crash();
    }

    @Override
    @StudentImplementationRequired
    public @NotNull String getName() {
        return crash();
    }
}
