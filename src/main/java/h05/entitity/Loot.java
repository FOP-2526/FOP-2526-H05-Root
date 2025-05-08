package h05.entitity;

import fopbot.FieldEntity;
import fopbot.World;
import h05.Mineable;

public class Loot extends FieldEntity {

    private final Mineable mineable;

    /**
     * Constructs and initializes a field entity at the specified {@code (x,y)} location in the
     * coordinate space.
     *
     * @param x the X coordinate of the newly constructed field entity
     * @param y the Y coordinate of the newly constructed field entity
     */
    public Loot(int x, int y, Mineable mineable) {
        super(x, y);
        this.mineable = mineable;
        World.getGlobalWorld().placeEntity(this);
    }

    public Mineable getMineable() {
        return mineable;
    }
}
