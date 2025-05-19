package h05.game;

import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.WorldUtilities;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.Miner;
import h05.entity.MiningRobot;
import h05.entity.RepairBot;
import h05.entity.Repairer;
import h05.gear.Axe;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.MiningDetector;
import h05.gear.Pickaxe;
import h05.gear.WallBreaker;
import h05.loot.Tree;
import h05.ui.FogDrawing;
import h05.ui.GearDrawing;
import h05.ui.LootDrawing;
import h05.ui.MineBotDrawing;
import h05.ui.WallFogDrawing;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoop {

    private static final int TICK_RATE = 1000;

    private final Timer loop = new Timer();

    private final TimerTask loopTask = new TimerTask() {

        @Override
        public void run() {
            for (Robot robot : robots) {
                if (robot instanceof Miner miner) {
                    miner.handleKeyInput(inputHandler.getDirection(), inputHandler.getSelection(), inputHandler.isPickGear(), inputHandler.isMine(), inputHandler.isInfo());
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
        int width = 10;
        int height = 10;
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
        World.getGlobalWorld().placeEntity(new Gear(4, 3, new Camera()));
        World.getGlobalWorld().placeEntity(new Gear(1, 0, new Pickaxe()));
        World.getGlobalWorld().placeEntity(new Gear(0, 1, new Axe()));
        World.getGlobalWorld().placeEntity(new Loot(3, 4, new Tree()));
        WorldUtilities.placeNewBattery();
        generateMaze(width, height);
    }

    protected void generateMaze(int width, int height) {
        for (int x = 0; x < width; x++) {
            World.placeHorizontalWall(x, 0);
            World.placeHorizontalWall(x, height - 1);
        }
        for (int y = 0; y < height; y++) {
            World.placeVerticalWall(0, y);
            World.placeVerticalWall(width - 1, y);
        }
        for (int x = 2; x < width; x += 4) {
            for (int y = 1; y < height; y++) {
                if (y % 2 == 1) {
                    World.placeVerticalWall(x, y);
                }
            }
        }
        for (int y = 2; y < height; y += 4) {
            for (int x = 1; x < width; x++) {
                if (x % 2 == 1) {
                    World.placeHorizontalWall(x, y);
                }
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
