package h05;

import fopbot.*;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.MiningRobot;
import h05.node.Node;
import h05.node.Tree;
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
            case Node node -> 0;
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
                                Map.entry(Tree.class, new TreeDrawing())
                        ))
                        .build(Comparator.comparingInt(this::getDrawingPriority))
        );
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                World.getGlobalWorld().placeEntity(new Fog(x, y));
            }
        }
        World.getGlobalWorld().placeEntity(new Tree(4, 4));
    }

    protected void setupRobots() {
        MiningRobot miner = new MiningRobot(5, 5);
        miner.equip(new WallBreaker());
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
