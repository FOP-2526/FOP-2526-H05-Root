package h05.entity;

import fopbot.Direction;
import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

@DoNotTouch
public class WallBreakerRepairBot extends AbstractRepairBot {

    @DoNotTouch
    public WallBreakerRepairBot(int x, int y, GameSettings settings, int radius) {
        super(x, y, settings, radius);
    }

    @DoNotTouch
    @Override
    protected void move(@NotNull Point point) {
        final int destX = point.x;
        final int destY = point.y;
        final GameSettings settings = getGameSettings();

        // Move to x coordinate
        if (destX != getX()) {
            Direction direction = destX < getX() ? Direction.LEFT : Direction.RIGHT;
            while (getDirection() != direction) {
                turnLeft();
            }
            while (destX != getX()) {
                if (!isFrontClear()) {
                    settings.removeEntity(settings.getWallAt(getX(), getY(), false));
                }
                move();
            }
        }
        // Move to y coordinate
        if (destY != getY()) {
            Direction direction = destY < getY() ? Direction.DOWN : Direction.UP;
            while (getDirection() != direction) {
                turnLeft();
            }
            while (destY != getY()) {
                if (!isFrontClear()) {
                    settings.removeEntity(settings.getWallAt(getX(), getY(), true));
                }
                move();
            }
        }
    }
}
