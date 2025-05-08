package h05.entitity;

import fopbot.FieldEntity;
import fopbot.World;
import h05.Equipment;

public class Loot extends FieldEntity {

    private final Equipment loot;

    public Loot(int x, int y, Equipment loot) {
        super(x, y);
        this.loot = loot;
        World.getGlobalWorld().placeEntity(this);
    }

    public Equipment getLoot() {
        return loot;
    }
}
