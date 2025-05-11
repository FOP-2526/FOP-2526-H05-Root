//package h05.gamecontrol;
//
//import fopbot.*;
//import fopbot.Robot;
//import h05.entity.MiningRobot;
//import h05.equipment.Battery;
//import h05.node.Node;
//import h05.node.Rock;
//import h05.node.Tree;
//import h05.ui.*;
//
//import java.util.*;
//
//import static h05.gamecontrol.GameConstants.TICK_DELAY;
//
/// **
// * A {@link GameControllerBase} controls the game loop and the {@link Robot}s and checks the win condition.
// */
//public abstract class GameControllerBase {
//    /**
//     * The {@link Timer} that controls the game loop.
//     */
//    private final Timer gameLoopTimer = new Timer();
//
//    /**
//     * The {@link GameInputHandler} that handles the input of the user.
//     */
//    private final GameInputHandler inputHandler = new GameInputHandler();
//
//    /**
//     * The {@link Robot}s that are controlled by the {@link GameControllerBase}.
//     */
//    protected final Set<Robot> robots = new HashSet<>();
//
//
//    protected Robot miningRobot;
//
//    /**
//     * A {@link Map} that maps a {@link Robot} to the amount of ticks that have passed since the last tick action.
//     */
//    private final Map<Robot, Integer> robotTicks = new HashMap<>();
//
//    /**
//     * The {@link TimerTask} that is executed every tick.
//     */
//    private final TimerTask gameLoopTask = new TimerTask() {
//        @Override
//        public void run() {
//            for (final Robot robot : GameControllerBase.this.robots) {
//                if (!(robot instanceof final TickBased tb)) {
//                    continue;
//                }
//                if (!GameControllerBase.this.robotTicks.containsKey(robot)) {
//                    GameControllerBase.this.robotTicks.put(robot, 0);
//                }
//                if (GameControllerBase.this.robotTicks.get(robot) < tb.getUpdateDelay()) {
//                    GameControllerBase.this.robotTicks.put(robot, GameControllerBase.this.robotTicks.get(robot) + 1);
//                    continue;
//                }
//                GameControllerBase.this.robotTicks.put(robot, 0);
//                if (robot instanceof final MiningRobot r) {
//                    r.handleKeyInput(
//                            GameControllerBase.this.inputHandler.getDirection(),
//                            GameControllerBase.this.inputHandler.getShouldMine()
//                    );
//                }
//            }
//        }
//    };
//
//    /**
//     * Gets the {@link MiningRobot} {@link Robot}.
//     *
//     * @return the {@link MiningRobot} {@link Robot}
//     */
//    public Robot getMiningRobot() {
//        return miningRobot;
//    }
//
//    /**
//     * Starts the game loop.
//     */
//    public void startGame() {
//        System.out.println("Starting game...");
//        System.out.println(GameConstants.getGameConstantsString());
//        this.gameLoopTimer.scheduleAtFixedRate(this.gameLoopTask, 0, TICK_DELAY);
//    }
//
//    /**
//     * Stops the game loop.
//     */
//    public void stopGame() {
//        this.gameLoopTimer.cancel();
//    }
//
//    /**
//     * Sets up the game.
//     */
//    protected void setup() {
//        setupWorld();
//        setupRobot();
//        this.inputHandler.install();
//    }
//
//    /**
//     * Initializes the {@link World} and adds the {@link Robot}s to it.
//     */
//    public void setupWorld() {
//        int width = 10;
//        int height = 10;
//        World.setSize(width, height);
//        World.setVisible(true);
//        World.getGlobalWorld().setDrawingRegistry(
//                DrawingRegistry.builder(DrawingRegistry.DEFAULT)
//                        .addAll(Map.ofEntries(
//                                Map.entry(Wall.class, new WallFogDrawing()),
//                                Map.entry(Gear.class, new GearDrawing()),
//                                Map.entry(Loot.class, new LootDrawing()),
//                                Map.entry(Fog.class, new FogDrawing()),
//                                Map.entry(Tree.class, new TreeDrawing()),
//                                Map.entry(Rock.class, new RockDrawing())
//                        ))
//                        .build(Comparator.comparingInt(GameControllerBase::getDrawingPriority))
//        );
//        boolean fog = true;
//        if (fog) {
//            for (int x = 0; x < width; x++) {
//                for (int y = 0; y < height; y++) {
//                    World.getGlobalWorld().placeEntity(new Fog(x, y));
//                }
//            }
//        }
//        World.getGlobalWorld().placeEntity(new Gear(3, 3, new Battery()));
//        World.getGlobalWorld().placeEntity(new Rock(2, 2));
//        World.getGlobalWorld().placeEntity(new Tree(2, 3));
//    }
//
//    /**
//     * Adds the {@link Robot}s to the {@link World}.
//     */
//    public void setupRobot() {
//        this.robots.add(miningRobot = new MiningRobot(
//                        5,
//                        5
//                )
//        );
//    }
//
//    public static int getDrawingPriority(FieldEntity entity) {
//        return switch (entity) {
//            case Wall w -> 0;
//            case Gear g -> 1;
//            case Loot l -> 1;
//            case Robot c -> 2;
//            case Fog fog -> 3;
//            case Node node -> 0;
//            default -> -1;
//        };
//    }
//
//}
//
//
