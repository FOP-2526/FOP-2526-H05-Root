package h05.entity;

import fopbot.FieldEntity;
import fopbot.World;
import h05.Equipment;

public class Gear extends FieldEntity {

    private final Equipment loot;

    public Gear(int x, int y, Equipment loot) {
        super(x, y);
        this.loot = loot;
        World.getGlobalWorld().placeEntity(this);
    }

    public Equipment getEquipment() {
        return loot;
    }
}
