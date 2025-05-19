package h05.AbstractMinableEntity;

import fopbot.Block;
import fopbot.World;
import h05.Mineable;
import h05.equipment.Tool;

public abstract class AbstractMinableEntity implements Mineable {
    private int durability;
    private final String name;
    private State miningState;

    public AbstractMinableEntity(String name) {
        this.name = name;
        this.durability = 100;
        this.miningState = State.NOT_MINED;
    }

    @Override
    public String getName() {
        return name;
    }

    public State getState() {
        return miningState;
    }

    public void setState(State state) {
        this.miningState = state;
    }

    public void reduceDurability(int amount) {
        durability -= amount;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
