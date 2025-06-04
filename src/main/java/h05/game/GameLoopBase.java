package h05.game;

import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.Miner;
import h05.entity.MiningRobot;
import h05.entity.Repairer;
import h05.ui.FogDrawing;
import h05.ui.GearDrawing;
import h05.ui.LootDrawing;
import h05.ui.MineBotDrawing;
import h05.ui.WallFogDrawing;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

@DoNotTouch
public abstract class GameLoopBase {

    @DoNotTouch
    private final Map<Robot, Integer> robotTicks = new HashMap<>();

    @DoNotTouch
    private final Timer loop = new Timer();

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
                    miner.handleKeyInput(inputHandler.getDirection(), inputHandler.getSelection(), inputHandler.isPickingGear(), inputHandler.isMining(), inputHandler.isInfo());
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
    private final int tickRate;

    @DoNotTouch
    private final GameInputHandler inputHandler = new GameInputHandler();

    @DoNotTouch
    private final Set<Robot> robots = new HashSet<>();

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
        int width = GameConstants.WORLD_WIDTH;
        int height = GameConstants.WORLD_HEIGHT;
        World.setSize(width, height);
        World.getGlobalWorld().setDrawingRegistry(
            DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                .addAll(Map.ofEntries(
                    Map.entry(Wall.class, new WallFogDrawing()),
                    Map.entry(Gear.class, new GearDrawing()),
                    Map.entry(Loot.class, new LootDrawing()),
                    Map.entry(MiningRobot.class, new MineBotDrawing()),
                    Map.entry(Fog.class, new FogDrawing())
                ))
                .build(Comparator.comparingInt(this::getDrawingPriority))
        );
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                World.getGlobalWorld().placeEntity(new Fog(x, y));
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
}
