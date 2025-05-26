package h05.loot;

import fopbot.World;
import h05.entity.Loot;

public interface Inventory {

    int capacity();

    int size();

    int numberOfItems();

    default boolean add(Loot loot) {
        if (loot.getMineable().getState() != MiningState.FULLY_MINED) {
            return false;
        }
        if (add(loot.getMineable())) {
            World.getGlobalWorld().removeEntity(loot);
            return true;
        }
        return false;

    }

    boolean add(Mineable item);

    String[] getNames();

    int getAmount(String itemName);
}
