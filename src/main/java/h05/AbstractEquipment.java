package h05;

import fopbot.FieldEntity;
import fopbot.World;

public class AbstractEquipment extends FieldEntity implements Equipment {

    private final String name;

    private int durability = 100;

    public AbstractEquipment(int x, int y, String name) {
        super(x, y);
        World.getGlobalWorld().placeEntity(this);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void setDurability(int durability) {
        if (durability < 0) {
            durability = 0;
        }
        this.durability = Math.min(durability, 100);
    }

    @Override
    public void increaseDurability(int amount) {
        durability = Math.min(durability + amount, 100);
    }

    @Override
    public void reduceDurability(int amount) {
        durability = Math.max(durability - amount, 0);
    }
}
