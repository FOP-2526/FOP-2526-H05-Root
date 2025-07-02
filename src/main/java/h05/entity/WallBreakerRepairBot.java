package h05.entity;

import fopbot.Direction;
import fopbot.Wall;
import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.SolutionOnly;

import java.awt.Point;

public class WallBreakerRepairBot extends AbstractRepairBot {

    public WallBreakerRepairBot(int x, int y, int radius) {
        super(x, y, radius);
    }

    @SolutionOnly
    @Override
    protected void move(@NotNull Point point) {
        final int x = point.x;
        final int y = point.y;
        final GameSettings settings = getGameSettings();

        // Move to x coordinate
        Direction direction = x < getX() ? Direction.LEFT : Direction.RIGHT;
        while (getDirection() != Direction.LEFT) {
            turnLeft();
        }
        while (x != getX()) {
            if (!isFrontClear()) {
                settings.removeEntity(settings.getWallAt(getX(), getY(), true));
            }
            move();
        }
        direction = y < getY() ? Direction.UP : Direction.DOWN;
        while (getDirection() != direction) {
            turnLeft();
        }
        // Move to y coordinate
        while (y != getY()) {
            if (!isFrontClear()) {
                settings.removeEntity(settings.getWallAt(getX(), getY(), false));
            }
            move();
        }
    }
}
