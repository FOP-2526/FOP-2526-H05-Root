package h05.game;

import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.MineBot;
import h05.entity.Miner;
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

/**
 * Base class for the game loop, handling the initialization and management of robots and world entities of
 * the MineBot environment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class GameLoopBase {

    /**
     * The roboters' ticks map, used to manage the update delay for each robot.
     */
    @DoNotTouch
    private final Map<Robot, Integer> robotTicks = new HashMap<>();

    /**
     * The timer used to schedule the game loop tasks.
     */
    @DoNotTouch
    private final Timer loop = new Timer();

    /**
     * The task that runs in the game loop, updating the robots and handling their actions.
     * This task is scheduled to run at a fixed rate defined by the tick rate.
     */
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

    /**
     * The tick rate of the game loop, defining how often the loop runs in milliseconds.
     * Default is set to {@link GameConstants#TICK_DELAY}.
     */
    @DoNotTouch
    private final int tickRate;

    /**
     * The input handler for the game, responsible for processing user inputs.
     */
    @DoNotTouch
    private final GameInputHandler inputHandler = new GameInputHandler();

    /**
     * A set of robots in the game, used to manage and update their states during the game loop.
     */
    @DoNotTouch
    private final Set<Robot> robots = new HashSet<>();

    /**
     * Constructs a new GameLoopBase with the specified tick rate.
     *
     * @param tickRate the tick rate in milliseconds, defining how often the game loop runs
     */
    @DoNotTouch
    public GameLoopBase(int tickRate) {
        this.tickRate = tickRate;
    }

    /**
     * Constructs a new GameLoopBase with the default tick rate defined in {@link GameConstants#TICK_DELAY}.
     */
    @DoNotTouch
    public GameLoopBase() {
        this(GameConstants.TICK_DELAY);
    }

    /**
     * Returns the priority for drawing the given entity.
     *
     * @param entity the field entity for which the drawing priority is determined
     *
     * @return the drawing priority of the entity, where lower values are drawn first
     */
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

    /**
     * Initializes the world.
     */
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
                    Map.entry(MineBot.class, new MineBotDrawing()),
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

    /**
     * Sets up the world with specific entities and structures after its initialization.
     */
    @DoNotTouch
    protected abstract void setupWorld();

    /**
     * Sets up the robots after its initialization.
     */
    @DoNotTouch
    protected void setupRobots() {
        for (FieldEntity entity : World.getGlobalWorld().getAllFieldEntities()) {
            if (entity instanceof Robot robot) {
                robots.add(robot);
            }
        }
    }

    /**
     * Initializes the robots in the game, adding specific robot types to the world.
     */
    @DoNotTouch
    protected abstract void initRobots();

    /**
     * Sets up the game environment, including the world and robots.
     * This method is called to prepare the game before it starts running.
     */
    @DoNotTouch
    public void setup() {
        initWorld();
        setupWorld();
        initRobots();
        setupRobots();
        World.setVisible(true);
        inputHandler.install();
    }

    /**
     * Starts the game loop, scheduling the loop task to run at a fixed rate defined by the tick rate.
     */
    @DoNotTouch
    public void start() {
        System.out.println(GameConstants.getGameConstantsString());
        setup();
        loop.scheduleAtFixedRate(loopTask, 0, tickRate);
    }

    /**
     * Stops the game loop, canceling the scheduled loop task.
     */
    @DoNotTouch
    public void stop() {
        loop.cancel();
    }

    /**
     * Returns the tick rate of the game loop.
     *
     * @return the tick rate in milliseconds
     */
    @DoNotTouch
    public int getTickRate() {
        return tickRate;
    }
}
