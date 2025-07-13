package h05.base.game;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.io.PropertyUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@DoNotTouch
public final class GameConstants {

    @DoNotTouch
    private static final @NotNull String PROPERTIES_FILE = "h05.properties";
    @DoNotTouch
    public static final int WORLD_WIDTH = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_WIDTH");
    @DoNotTouch
    public static final int WORLD_HEIGHT = PropertyUtils.getIntProperty(PROPERTIES_FILE, "WORLD_HEIGHT");
    @DoNotTouch
    public static final int TICK_DELAY = PropertyUtils.getIntProperty(PROPERTIES_FILE, "TICK_DELAY");
    @DoNotTouch
    public static final boolean FOG_VISIBILITY = PropertyUtils.getBooleanProperty(PROPERTIES_FILE, "FOG_VISIBILITY");
    @DoNotTouch
    private static final long _RANDOM_SEED = PropertyUtils.getLongProperty(PROPERTIES_FILE, "RANDOM_SEED");
    @DoNotTouch
    private static final long RANDOM_SEED = _RANDOM_SEED == 0 ? ThreadLocalRandom.current().nextLong() : _RANDOM_SEED;
    @DoNotTouch
    public static @NotNull Random RANDOM_GENERATOR = new Random(RANDOM_SEED);

    @DoNotTouch
    private GameConstants() {
    }

    @DoNotTouch
    public static @NotNull String getGameConfigurationString() {
        return """
            World width: %d
            World height: %d
            Tick delay: %d
            Random seed: %d
            """.formatted(WORLD_WIDTH, WORLD_HEIGHT, TICK_DELAY, RANDOM_SEED);
    }
}
