package h05.base.game;

import fopbot.*;
import fopbot.Robot;
import h05.base.entity.Fog;
import h05.base.entity.Gear;
import h05.base.entity.Loot;
import h05.base.ui.FogDrawing;
import h05.base.ui.GearDrawing;
import h05.base.ui.LootDrawing;
import h05.base.ui.WallFogDrawing;
import h05.entity.Miner;
import h05.entity.Repairer;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.*;
import java.util.*;

@DoNotTouch
public abstract class GameLoopBase {

    @DoNotTouch
    private final Set<Robot> robots = new HashSet<>();

    @DoNotTouch
    private final GameInputHandler inputHandler = new GameInputHandler();

    @DoNotTouch
    private final Map<Robot, Integer> robotTicks = new HashMap<>();

    @DoNotTouch
    private final int tickRate;

    @DoNotTouch
    private final Timer loop = new Timer();

    @DoNotTouch
    private final @NotNull GameSettings settings = new BasicGameSettings();

    @DoNotTouch
    private final TimerTask loopTask = new TimerTask() {
        @Override
        public void run() {
            for (Robot robot : robots) {
                if (!(robot instanceof final TickBased tb)) {
                    continue;
                }
                if (!robotTicks.containsKey(robot)) {
                    robotTicks.put(robot, 0);
                }
                if (robotTicks.get(robot) < tb.getUpdateDelay()) {
                    robotTicks.put(robot, robotTicks.get(robot) + 1);
                    continue;
                }
                robotTicks.put(robot, 0);

                if (robot instanceof Miner miner) {
                    miner.handleKeyInput(
                            inputHandler.getDirection(),
                            inputHandler.getSelection(),
                            inputHandler.isPickingGear(),
                            inputHandler.isMining(),
                            inputHandler.isInfo()
                    );
                }
                if (robot instanceof Repairer repairer) {
                    Point point = repairer.scan();
                    if (point != null) {
                        repairer.repair(point);
                    }
                }
            }
        }
    };

    @DoNotTouch
    public GameLoopBase(int tickRate) {
        this.tickRate = tickRate;
    }

    @DoNotTouch
    public GameLoopBase() {
        this(GameConstants.TICK_DELAY);
    }

    @DoNotTouch
    protected int getDrawingPriority(FieldEntity entity) {
        return switch (entity) {
            case Wall w -> 0;
            case Gear g -> 1;
            case Loot l -> 1;
            case Robot c -> 2;
            case Fog fog -> 3;
            default -> -1;
        };
    }

    @DoNotTouch
    protected void initWorld() {
        final int width = GameConstants.WORLD_WIDTH;
        final int height = GameConstants.WORLD_HEIGHT;
        World.setSize(width, height);
        final KarelWorld world = World.getGlobalWorld();
        world.setDrawingRegistry(
                DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                        .addAll(
                                Map.ofEntries(
                                        Map.entry(Wall.class, new WallFogDrawing()),
                                        Map.entry(Gear.class, new GearDrawing()),
                                        Map.entry(Loot.class, new LootDrawing()),
                                        // Map.entry(MineBot.class, new MineBotDrawing()),
                                        Map.entry(Fog.class, new FogDrawing())
                                )
                        ).build(Comparator.comparingInt(this::getDrawingPriority))
        );
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world.placeEntity(new Fog(x, y));
            }
        }
    }

    @DoNotTouch
    protected abstract void setupWorld();

    @DoNotTouch
    protected void setupRobots() {
        for (FieldEntity entity : World.getGlobalWorld().getAllFieldEntities()) {
            if (entity instanceof Robot robot) {
                robots.add(robot);
            }
        }
    }

    @DoNotTouch
    protected abstract void initRobots();

    @DoNotTouch
    public void setup() {
        initWorld();
        setupWorld();
        initRobots();
        setupRobots();
        World.setVisible(true);
        inputHandler.install();
    }

    @DoNotTouch
    public void start() {
        System.out.println(GameConstants.getGameConfigurationString());
        setup();
        loop.scheduleAtFixedRate(loopTask, 0, tickRate);
    }

    @DoNotTouch
    public void stop() {
        loop.cancel();
    }

    @DoNotTouch
    public int getTickRate() {
        return tickRate;
    }

    @DoNotTouch
    public @NotNull GameSettings getGameSettings() {
        return settings;
    }
}
