package h05.game;

import fopbot.Robot;
import fopbot.*;
import h05.WorldUtilities;
import h05.entity.*;
import h05.gear.*;
import h05.loot.Tree;
import h05.ui.*;

import java.awt.*;
import java.util.*;

public class GameLoop {

    private final Map<Robot, Integer> robotTicks = new HashMap<>();

    private final Timer loop = new Timer();

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

    private final int tickRate;

    private final GameInputHandler inputHandler = new GameInputHandler();

    private final Set<Robot> robots = new HashSet<>();

    public GameLoop(int tickRate) {
        this.tickRate = tickRate;
    }

    public GameLoop() {
        this(GameConstants.TICK_DELAY);
    }

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

    protected void setUpWorld() {
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
        World.getGlobalWorld().placeEntity(new Gear(4, 4, new Battery()));
        World.getGlobalWorld().placeEntity(new Gear(1, 0, new Pickaxe()));
        World.getGlobalWorld().placeEntity(new Gear(0, 1, new Axe()));
        World.getGlobalWorld().placeEntity(new Loot(3, 4, new Tree()));
        WorldUtilities.placeNewBattery();
    }

    protected void generateMaze() {
        for (int x = 0; x < 10; x++) {
            World.placeHorizontalWall(x, 9); // Top
        }
        for (int y = 0; y < 10; y++) {
            if (y != 0) World.placeVerticalWall(0, y);
            World.placeVerticalWall(9, y);
        }

        for (int y = 1; y < 9; y += 2) {
            for (int x = 1; x < 9; x++) {
                World.placeHorizontalWall(x, y);
            }
        }

        for (int x = 2; x < 9; x += 2) {
            for (int y = 1; y < 9; y++) {
                World.placeVerticalWall(x, y);
            }
        }
    }


    protected void setupRobots() {
        MiningRobot miner = new MiningRobot(0, 0);
        miner.equip(new WallBreaker());
        miner.equip(new MiningDetector());
        robots.add(miner);
        RepairBot repairer = new RepairBot(3, 3, 3);
        robots.add(repairer);
    }

    public void setup() {
        setUpWorld();
        setupRobots();
        World.setVisible(true);
        inputHandler.install();
    }

    public void start() {
        setup();
        loop.scheduleAtFixedRate(loopTask, 0, tickRate);
    }

    public void stop() {
        loop.cancel();
    }

    public int getTickRate() {
        return tickRate;
    }
}
