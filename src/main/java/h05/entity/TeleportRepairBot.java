package h05.entity;

import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

import java.awt.Point;

public class TeleportRepairBot extends AbstractRepairBot {

    public TeleportRepairBot(int x, int y, GameSettings settings, int radius) {
        super(x, y, settings, radius);
    }

    @SolutionOnly
    @Override
    protected void move(@NotNull Point point) {
        setField(point.x, point.y);
    }
}
