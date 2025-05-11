package h05.gamecontrol;

import java.util.concurrent.ThreadLocalRandom;

import static org.tudalgo.algoutils.student.io.PropertyUtils.getIntProperty;
import static org.tudalgo.algoutils.student.io.PropertyUtils.getLongProperty;

public final class GameConstants {
    public static int WORLD_WIDTH = 9;
    public static int WORLD_HEIGHT = 9;
    public static final int TICK_DELAY = 100;
    private static final long _RANDOM_SEED = 0L;
    public static long RANDOM_SEED = _RANDOM_SEED == 0 ? ThreadLocalRandom.current().nextLong() : _RANDOM_SEED;

    /**
     * Gets a string containing all game constants.
     *
     * @return a string containing all game constants
     */
    public static String getGameConstantsString() {
        return String.format(
            """
                World width: %d
                World height: %d
                Tick delay: %d
                Random seed: %d""",
            WORLD_WIDTH,
            WORLD_HEIGHT,
            TICK_DELAY,
            RANDOM_SEED
        );
    }
}
