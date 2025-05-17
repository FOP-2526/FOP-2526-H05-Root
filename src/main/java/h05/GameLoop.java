package h05;

import fopbot.*;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.MiningRobot;
import h05.AbstractMinableEntity.AbstractMinableEntity;
import h05.AbstractMinableEntity.Rock;
import h05.AbstractMinableEntity.Tree;
import h05.equipment.Battery;
import h05.ui.*;

import java.util.*;

public class GameLoop {

    private static final int TICK_RATE = 1000;

    private final Timer loop = new Timer();

    private final TimerTask loopTask = new TimerTask() {

        @Override
        public void run() {
            for (Robot robot : robots) {
                if (robot instanceof Miner miner) {
                    miner.handleKeyInput(inputHandler.getDirection(), inputHandler.getSelection(), inputHandler.isPickGear(), inputHandler.isMine());
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
            case AbstractMinableEntity node -> 0;
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
                    Map.entry(Fog.class, new FogDrawing()),
                    Map.entry(Tree.class, new NodeDrawing()),
                    Map.entry(Rock.class, new NodeDrawing())
                ))
                .build(Comparator.comparingInt(this::getDrawingPriority))
        );
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                World.getGlobalWorld().placeEntity(new Fog(x, y));
            }
        }
        World.getGlobalWorld().placeEntity(new Tree(4, 4));
        World.getGlobalWorld().placeEntity(new Rock(4, 3));
        World.getGlobalWorld().placeEntity(new Gear(3, 4, new Battery()));

        World.placeHorizontalWall(0, 0);
        World.placeHorizontalWall(0, 1);
        World.placeHorizontalWall(0, 2);
        World.placeHorizontalWall(0, 3);
        World.placeHorizontalWall(0, 4);
        World.placeHorizontalWall(0, 5);
        World.placeHorizontalWall(0, 6);
        World.placeHorizontalWall(0, 7);
        World.placeHorizontalWall(0, 8);
        World.placeHorizontalWall(0, 9);

        World.placeHorizontalWall(1, 3);
        World.placeHorizontalWall(1, 4);
        World.placeHorizontalWall(1, 5);
        World.placeHorizontalWall(1, 6);
        World.placeHorizontalWall(1, 7);

        World.placeHorizontalWall(2, 0);
        World.placeHorizontalWall(2, 1);
        World.placeHorizontalWall(2, 2);
        World.placeHorizontalWall(2, 3);
        World.placeHorizontalWall(2, 4);
        World.placeHorizontalWall(2, 5);
        World.placeHorizontalWall(2, 6);
        World.placeHorizontalWall(2, 7);
        World.placeHorizontalWall(2, 8);
        World.placeHorizontalWall(2, 9);

        World.placeHorizontalWall(3, 1);
        World.placeHorizontalWall(3, 3);
        World.placeHorizontalWall(3, 4);
        World.placeHorizontalWall(3, 6);

        World.placeHorizontalWall(4, 0);
        World.placeHorizontalWall(4, 1);
        World.placeHorizontalWall(4, 2);
        World.placeHorizontalWall(4, 3);
        World.placeHorizontalWall(4, 4);
        World.placeHorizontalWall(4, 5);
        World.placeHorizontalWall(4, 6);
        World.placeHorizontalWall(4, 7);
        World.placeHorizontalWall(4, 8);
        World.placeHorizontalWall(4, 9);

        World.placeVerticalWall(1, 1);
        World.placeVerticalWall(2, 0);
        World.placeVerticalWall(2, 1);
        World.placeVerticalWall(2, 2);
        World.placeVerticalWall(3, 1);
        World.placeVerticalWall(3, 3);
        World.placeVerticalWall(4, 2);
        World.placeVerticalWall(4, 4);
        World.placeVerticalWall(5, 0);
        World.placeVerticalWall(5, 1);
        World.placeVerticalWall(5, 2);
        World.placeVerticalWall(5, 3);
        World.placeVerticalWall(5, 4);
        World.placeVerticalWall(6, 1);
        World.placeVerticalWall(6, 3);
        World.placeVerticalWall(7, 2);
        World.placeVerticalWall(7, 4);
        World.placeVerticalWall(8, 0);
        World.placeVerticalWall(8, 1);
        World.placeVerticalWall(8, 2);
        World.placeVerticalWall(8, 3);
        World.placeVerticalWall(8, 4);

    }

    protected void setupRobots() {
        MiningRobot miner = new MiningRobot(0, 0);
        miner.equip(new WallBreaker());
        miner.equip(new MiningDetector());
        robots.add(miner);
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
