package h05.game;

import org.tudalgo.algoutils.student.io.PropertyUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class GameConstants {

    private static final String PROPERTIES_FILE = "h05.properties";
    private static final long _RANDOM_SEED = PropertyUtils.getLongProperty(PROPERTIES_FILE, "RANDOM_SEED");
    private static final long RANDOM_SEED = _RANDOM_SEED == 0 ? ThreadLocalRandom.current().nextLong() : _RANDOM_SEED;

    public static final int WORLD_WIDTH = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_WIDTH");
    public static final int WORLD_HEIGHT = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_HEIGHT");
    public static final int TICK_DELAY = PropertyUtils.getIntProperty(PROPERTIES_FILE, "TICK_DELAY");
    public static final Random RANDOM_GENERATOR = new Random(RANDOM_SEED);

    public static final boolean FOG_VISIBILITY = PropertyUtils.getBooleanProperty(PROPERTIES_FILE, "FOG_VISIBILITY");

    private GameConstants() {
    }

    public static String getGameConstantsString() {
        return """
            World width: %d
            World height: %d
            Tick delay: %d
            Random seed: %d
            """.formatted(WORLD_WIDTH, WORLD_HEIGHT, TICK_DELAY, RANDOM_SEED);
    }
}
