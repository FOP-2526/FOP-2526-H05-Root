package h05.gear;

import fopbot.Direction;
import fopbot.Wall;
import fopbot.World;
import h05.WorldUtilities;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class WallBreaker extends AbstractEquipment implements UsableEquipment {

    @DoNotTouch
    public WallBreaker() {
        super("Wall Breaker");
    }

    @DoNotTouch
    @Override
    public void use(int x, int y, @NotNull Direction direction) {
        if (getCondition() == Condition.BROKEN) {
            return;
        }
        int removeX = x;
        int removeY = y;
        if (direction == Direction.DOWN) {
            removeY--;
        } else if (direction == Direction.LEFT) {
            removeX--;
        }
        if (removeX < 0 || removeX >= World.getWidth() || removeY < 0 || removeY >= World.getHeight()) {
            return;
        }
        boolean isHorizontal = direction == Direction.UP || direction == Direction.DOWN;
        Wall wall = WorldUtilities.getWallAtPoint(removeX, removeY);
        if (wall != null && wall.isHorizontal() == isHorizontal) {
            World.getGlobalWorld().removeEntity(wall);
            reduceDurability(100);
        }
    }
}
