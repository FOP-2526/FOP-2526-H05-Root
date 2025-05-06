package h05.node;


public class TreeNode extends Node {
    public TreeNode(int x, int y) {
        super(x, y);
        this.setMiningState(MiningState.NOT_MINED);
    }

    @Override
    public String getName() {
        return "tree";
    }
}
