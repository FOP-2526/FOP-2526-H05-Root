package h05.node;

import fopbot.FieldEntity;
import h05.Mineable;

public abstract class Node extends FieldEntity implements Mineable {
    private MiningState miningState;
    public Node(int x, int y) {
        super(x, y);
    }

    @Override
    public MiningState getMiningState() {
       return miningState;
    }

    @Override
    public void setMiningState(MiningState miningState) {
        this.miningState = miningState;
    }
}
