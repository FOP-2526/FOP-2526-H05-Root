package h05.equipment;

import fopbot.Direction;
import fopbot.Wall;
import fopbot.World;
import h05.base.game.GameSettings;
import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class WallBreaker extends AbstractEquipment implements UsableEquipment {

    @DoNotTouch
    public WallBreaker() {
        super("WallBreaker");
    }

    @StudentImplementationRequired
    @Override
    public void use(@NotNull Miner miner) {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return;
        }
        int removeX = miner.getX();
        int removeY = miner.getY();
        final Direction direction = miner.getDirection();
        if (direction == Direction.DOWN) {
            removeY--;
        } else if (direction == Direction.LEFT) {
            removeX--;
        }
        if (removeX < 0 || removeX >= World.getWidth() || removeY < 0 || removeY >= World.getHeight()) {
            return;
        }

        final boolean isHorizontal = direction == Direction.UP || direction == Direction.DOWN;
        final GameSettings settings = miner.getGameSettings();
        final Wall[] walls = settings.getWallsAt(removeX, removeY);

        for (Wall wall : walls) {
            if (wall.isHorizontal() == isHorizontal) {
                settings.removeEntity(wall);
                reduceDurability(100);
            }
        }
    }
}
