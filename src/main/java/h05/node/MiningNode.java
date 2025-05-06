package h05.node;

public class MiningNode extends Node {
    public MiningNode(int x, int y) {
        super(x, y);
        this.setMiningState(MiningState.NOT_MINED);
    }

    @Override
    public String getName() {
        return "mining";
    }
}