package h05.equipment;

import h05.AbstractDurableObject;
import h05.base.game.BasicGameSettings;
import h05.base.game.GameConstants;
import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

@SolutionOnly
public abstract class AbstractEquipment extends AbstractDurableObject implements Equipment {

    private final @NotNull String name;

    public AbstractEquipment(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }
}
