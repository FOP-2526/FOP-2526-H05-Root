package h05.AbstractMinableEntity;

import fopbot.Block;
import fopbot.World;
import h05.Mineable;
import h05.equipment.Tool;

public abstract class AbstractMinableEntity extends Block implements Mineable {
    private State miningState;
    private int durability;
    private final String name;

    public AbstractMinableEntity(int x, int y, String name) {
        super(x, y);
        this.name = name;
        this.miningState = State.NOT_MINED;
        this.durability = 100;
    }

    @Override
    public String getName() {
        return name;
    }

    public State getState() {
        return miningState;
    }

    public void setMiningState(State miningState) {
        this.miningState = miningState;
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
