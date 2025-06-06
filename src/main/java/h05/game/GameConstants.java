package h05.game;

import org.tudalgo.algoutils.student.io.PropertyUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Defines constants for the MineBot environment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public final class GameConstants {

    /**
     * The name of the properties file containing game constants.
     */
    private static final String PROPERTIES_FILE = "h05.properties";

    /**
     * The random seed used for generating random numbers in the game.
     */
    private static final long _RANDOM_SEED = PropertyUtils.getLongProperty(PROPERTIES_FILE, "RANDOM_SEED");

    /**
     * The random seed used for generating random numbers in the game.
     */
    private static final long RANDOM_SEED = _RANDOM_SEED == 0 ? ThreadLocalRandom.current().nextLong() : _RANDOM_SEED;

    /**
     * The width of the game world.
     */
    public static final int WORLD_WIDTH = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_WIDTH");

    /**
     * The height of the game world.
     */
    public static final int WORLD_HEIGHT = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_HEIGHT");

    /**
     * The delay between game ticks, in milliseconds.
     */
    public static final int TICK_DELAY = PropertyUtils.getIntProperty(PROPERTIES_FILE, "TICK_DELAY");

    /**
     * The random number generator used in the game, seeded with {@link GameConstants#RANDOM_SEED}.
     */
    public static Random RANDOM_GENERATOR = new Random(RANDOM_SEED);

    /**
     * The visibility of the fog in the game used for debugging purposes.
     */
    public static final boolean FOG_VISIBILITY = PropertyUtils.getBooleanProperty(PROPERTIES_FILE, "FOG_VISIBILITY");

    /**
     * Don't let anyone instantiate this class.
     */
    private GameConstants() {
    }

    /**
     * Returns a string representation of the game constants.
     *
     * @return a string containing the game constants
     */
    public static String getGameConstantsString() {
        return """
            World width: %d
            World height: %d
            Tick delay: %d
            Random seed: %d
            """.formatted(WORLD_WIDTH, WORLD_HEIGHT, TICK_DELAY, RANDOM_SEED);
    }
}
