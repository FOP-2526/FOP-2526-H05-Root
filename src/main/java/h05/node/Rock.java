package h05.node;

public class Rock extends Node {
    public Rock(int x, int y) {
        super(x, y);
    }

    @Override
    public String getName() {
        return "mining";
    }
}
