package h05;

public interface Mineable extends Durable {

    String getName();

    State getState();

    void setState(State state);

    enum State {
        NOT_MINED,
        HALF_MINED,
        FULLY_MINED
    }
}
