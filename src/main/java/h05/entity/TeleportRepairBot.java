package h05.entity;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

import java.awt.Point;

public class TeleportRepairBot extends AbstractRepairBot {

    public TeleportRepairBot(int x, int y, int radius) {
        super(x, y, radius);
    }

    @SolutionOnly
    @Override
    protected void move(@NotNull Point point) {
        setField(point.x, point.y);
    }
}
