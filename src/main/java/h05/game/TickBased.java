package h05.game;

/**
 * An interface representing entities that are updated based on ticks.
 */
public interface TickBased {

    /**
     * Returns the number of ticks after which the entity should be updated.
     *
     * @return the update delay in ticks
     */
    default int getUpdateDelay() {
        return 0;
    }
}
