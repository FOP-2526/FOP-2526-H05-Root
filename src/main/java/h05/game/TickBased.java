package h05.game;

public interface TickBased {

    default int getUpdateDelay() {
        return 0;
    }
}
