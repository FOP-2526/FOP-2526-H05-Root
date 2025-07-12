package h05.entity;

import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;


@DoNotTouch
public class TeleportRepairBot extends AbstractRepairBot {

    @DoNotTouch
    public TeleportRepairBot(int x, int y, GameSettings settings, int radius) {
        super(x, y, settings, radius);
    }

    @DoNotTouch
    @Override
    protected void move(@NotNull Point point) {
        setField(point.x, point.y);
    }
}
