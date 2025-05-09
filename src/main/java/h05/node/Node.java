package h05.node;

import fopbot.FieldEntity;
import h05.Mineable;

public abstract class Node extends FieldEntity implements Mineable {
    private State miningState;
    private int durability;
    public Node(int x, int y) {
        super(x, y);
        this.miningState = State.NOT_MINED;
        this.durability = 100;
    }

    public void reduceDurability(int amount) {
        durability -= amount;
    }

    public int getDurability() {
        return durability;
    }
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    public State getMiningState() {
       return miningState;
    }

    public void setMiningState(State miningState) {
        this.miningState = miningState;
    }
}
