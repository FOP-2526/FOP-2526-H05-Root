package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;

public interface UsableEquipment extends Equipment {

    void use(@NotNull Miner miner);
}
