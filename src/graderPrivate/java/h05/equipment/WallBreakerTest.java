package h05.equipment;

import fopbot.Direction;
import fopbot.Wall;
import fopbot.World;
import h05.Utils;
import h05.base.game.BasicGameSettings;
import h05.base.game.GameSettings;
import h05.entity.Miner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.awt.Point;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class WallBreakerTest {

    private final Context context = contextBuilder()
        .add("world layout", "world size: 3x3, Miner at (1, 1), surrounded by walls on all sides")
        .build();
    private final AtomicReference<Direction> currentDirection = new AtomicReference<>();
    private AtomicReference<Point> getWallsAt_argsRef;
    private GameSettings gameSettingsMock;
    private Miner minerMock;

    @BeforeEach
    public void setup() {
        World.setSize(3, 3);
        World.setVisible(false);
        World.setDelay(0);
        World.placeHorizontalWall(1, 1);
        World.placeHorizontalWall(1, 0);
        World.placeVerticalWall(1, 1);
        World.placeVerticalWall(0, 1);

        getWallsAt_argsRef = new AtomicReference<>();
        Answer<?> gameSettingsAnswer = invocation -> {
            if (Utils.methodSignatureEquals(invocation.getMethod(), "getWallsAt", int.class, int.class)) {
                getWallsAt_argsRef.set(new Point(invocation.getArgument(0, Integer.class), invocation.getArgument(1, Integer.class)));
            }
            return invocation.callRealMethod();
        };
        gameSettingsMock = Mockito.mock(BasicGameSettings.class, gameSettingsAnswer);
        Answer<?> minerAnswer = invocation -> {
            Method invokedMethod = invocation.getMethod();
            if (Utils.methodSignatureEquals(invokedMethod, "getX") || Utils.methodSignatureEquals(invokedMethod, "getY")) {
                return 1;
            } else if (Utils.methodSignatureEquals(invokedMethod, "getDirection")) {
                return currentDirection.get();
            } else if (Utils.methodSignatureEquals(invokedMethod, "getGameSettings")) {
                return gameSettingsMock;
            } else {
                return Mockito.RETURNS_DEFAULTS.answer(invocation);
            }
        };
        minerMock = Mockito.mock(Miner.class, minerAnswer);
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testUse_callsGetWallsAt(Direction direction) {
        currentDirection.set(direction);
        WallBreaker instance = callObject(WallBreaker::new);
        call(() -> instance.use(minerMock));
        Point args = getWallsAt_argsRef.get();
        assertNotNull(args, context, r -> "WallBreaker.use(Miner) did not call GameSettings.getWallsAt(int, int)");
        assertEquals(direction == Direction.LEFT ? 0 : 1, args.x, context, r ->
            "WallBreaker.use(Miner) did not call GameSettings.getWallsAt(int, int) with the correct arguments (wrong x coordinate)");
        assertEquals(direction == Direction.DOWN ? 0 : 1, args.y, context, r ->
            "WallBreaker.use(Miner) did not call GameSettings.getWallsAt(int, int) with the correct arguments (wrong y coordinate)");
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testUse_removesWalls(Direction direction) {
        currentDirection.set(direction);
        WallBreaker instance = callObject(WallBreaker::new);
        call(() -> instance.use(minerMock));

        List<Wall> walls = World.getGlobalWorld()
            .getAllFieldEntities()
            .stream()
            .filter(fieldEntity -> fieldEntity instanceof Wall)
            .map(Wall.class::cast)
            .toList();
        assertEquals(3, walls.size(), context, r -> "WallBreaker.use(Miner) did not remove any walls");
        int x = direction == Direction.LEFT ? 0 : 1;
        int y = direction == Direction.DOWN ? 0 : 1;
        boolean horizontal = direction.isHorizontal();
        assertFalse(walls.stream().anyMatch(wall -> wall.getX() == x && wall.getY() == y && wall.isHorizontal() != horizontal),
            context,
            r -> "WallBreaker.use(Miner) did not remove the correct wall (direction: %s)".formatted(direction));
    }
}
