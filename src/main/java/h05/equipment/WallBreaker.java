package h05.equipment;

import fopbot.Wall;
import fopbot.World;

public class WallBreaker extends AbstractEquipment {

    public WallBreaker() {
        super("Wall Breaker");
    }

    public void breakWall(int x, int y) {
        if (getCondition() == Condition.BROKEN) {
            return;
        }
        World.getGlobalWorld().removeEntity(x, y, Wall.class);
        reduceDurability(100);
    }
}
