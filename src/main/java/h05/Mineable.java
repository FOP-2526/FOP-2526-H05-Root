package h05;

public interface Mineable extends Durable {

    String getName();

    default State getState() {
        if (getDurability() > 50) {
            return State.NOT_MINED;
        } else if (getDurability() > 0) {
            return State.HALF_MINED;
        } else {
            return State.FULLY_MINED;
        }
    }

    enum State {
        NOT_MINED,
        HALF_MINED,
        FULLY_MINED
    }
}
