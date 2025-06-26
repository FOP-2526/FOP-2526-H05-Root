package h05;

import fopbot.KarelWorld;
import fopbot.World;
import h05.game.GameLoopBase;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * The main game loop responsible for updating the game state and processing actions during each tick or frame of
 * the simulation.
 *
 * @see GameLoopBase
 */
@DoNotTouch
public class GameLoop extends GameLoopBase {

    @StudentImplementationRequired
    @Override
    protected void setupWorld() {
        World.placeHorizontalWall(0, 0);
        World.placeVerticalWall(0, 1);
        World.placeHorizontalWall(0, 3);
        World.placeHorizontalWall(0, 5);
        World.placeHorizontalWall(0, 6);
        World.placeVerticalWall(1, 0);
        World.placeVerticalWall(1, 1);
        World.placeHorizontalWall(1, 2);
        World.placeVerticalWall(1, 3);
        World.placeHorizontalWall(1, 4);
        World.placeVerticalWall(1, 4);
        World.placeHorizontalWall(1, 5);
        World.placeHorizontalWall(1, 6);
        World.placeVerticalWall(2, 1);
        World.placeVerticalWall(2, 2);
        World.placeVerticalWall(2, 3);
        World.placeHorizontalWall(2, 4);
        World.placeHorizontalWall(2, 5);
        World.placeHorizontalWall(2, 6);
        World.placeVerticalWall(3, 1);
        World.placeHorizontalWall(3, 2);
        World.placeHorizontalWall(2, 3);
        World.placeHorizontalWall(3, 4);
        World.placeVerticalWall(3, 4);
        World.placeVerticalWall(3, 5);
        World.placeHorizontalWall(3, 6);
        World.placeVerticalWall(4, 0);
        World.placeHorizontalWall(4, 1);
        World.placeHorizontalWall(4, 2);
        World.placeHorizontalWall(4, 3);
        World.placeHorizontalWall(4, 5);
        World.placeHorizontalWall(4, 6);
        World.placeHorizontalWall(5, 1);
        World.placeVerticalWall(5, 1);
        World.placeHorizontalWall(5, 2);
        World.placeHorizontalWall(5, 3);
        World.placeVerticalWall(5, 3);
        World.placeHorizontalWall(5, 4);
        World.placeHorizontalWall(5, 5);
        World.placeHorizontalWall(5, 6);
        World.placeVerticalWall(6, 0);
        World.placeHorizontalWall(6, 1);
        World.placeVerticalWall(6, 1);
        World.placeVerticalWall(6, 2);
        World.placeVerticalWall(6, 3);
        World.placeHorizontalWall(6, 4);
        World.placeVerticalWall(6, 4);
        World.placeVerticalWall(6, 5);
        World.placeHorizontalWall(6, 6);
        World.placeVerticalWall(6, 6);

        KarelWorld world = World.getGlobalWorld();
//        world.placeEntity(new Gear(3, 3, new Battery()));
//        world.placeEntity(new Gear(0, 6, new Battery()));
//        world.placeEntity(new Gear(2, 0, new Pickaxe()));
//        world.placeEntity(new Gear(6, 3, new Axe()));
//        world.placeEntity(new Loot(0, 3, new Tree()));
//        world.placeEntity(new Loot(6, 0, new Tree()));
//        world.placeEntity(new Loot(3, 4, new Tree()));
//        world.placeEntity(new Loot(6, 5, new Rock()));
//        world.placeEntity(new Loot(3, 0, new Rock()));
//        world.placeEntity(new Loot(0, 0, new Rock()));
//        world.placeEntity(new Gear(3, 6, new MiningDetector()));
//        world.placeEntity(new Gear(6, 6, new WallBreaker()));
//        world.placeEntity(new Gear(5, 0, new WallBreaker()));
//        world.placeEntity(new Gear(2, 2, new TelephotoLens(1)));
//        world.placeEntity(new Gear(5, 3, new TelephotoLens(1)));
//        world.placeEntity(new Gear(6, 4, new Powerbank(50)));
    }

    @StudentImplementationRequired
    @Override
    protected void initRobots() {
//        MineBot miner = new MineBot(1, 0);
//        RepairBot repairer1 = new RepairBot(3, 2, 2);
//        RepairBot repairer2 = new RepairBot(4, 3, 2);
    }
}
