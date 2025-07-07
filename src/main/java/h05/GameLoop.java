package h05;

import fopbot.KarelWorld;
import fopbot.World;
import h05.base.entity.Gear;
import h05.base.entity.Loot;
import h05.base.game.GameLoopBase;
import h05.base.game.GameSettings;
import h05.entity.MineBot;
import h05.entity.Miner;
import h05.entity.Repairer;
import h05.entity.TeleportRepairBot;
import h05.entity.WallBreakerRepairBot;
import h05.equipment.Axe;
import h05.equipment.Battery;
import h05.equipment.Pickaxe;
import h05.equipment.Powerbank;
import h05.equipment.TelephotoLens;
import h05.equipment.WallBreaker;
import h05.mineable.Rock;
import h05.mineable.Tree;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

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
        world.placeEntity(new Gear(3, 3, new Battery()));
        world.placeEntity(new Gear(0, 6, new Battery()));
        world.placeEntity(new Gear(2, 0, new Pickaxe()));
        world.placeEntity(new Gear(6, 3, new Axe()));
        world.placeEntity(new Loot(0, 3, new Tree()));
        world.placeEntity(new Loot(6, 0, new Tree()));
        world.placeEntity(new Loot(3, 4, new Tree()));
        world.placeEntity(new Loot(6, 5, new Rock()));
        world.placeEntity(new Loot(3, 0, new Rock()));
        world.placeEntity(new Loot(0, 0, new Rock()));
        world.placeEntity(new Gear(6, 6, new WallBreaker()));
        world.placeEntity(new Gear(5, 0, new WallBreaker()));
        world.placeEntity(new Gear(2, 2, new TelephotoLens(1)));
        world.placeEntity(new Gear(5, 3, new TelephotoLens(1)));
        world.placeEntity(new Gear(6, 4, new Powerbank(50)));
    }

    @StudentImplementationRequired
    @Override
    protected void initRobots() {
        GameSettings settings = getGameSettings();
        Miner miner = new MineBot(0, 0, settings);
        Repairer repairer1 = new TeleportRepairBot(3, 2, settings, 2);
        Repairer repairer2 = new WallBreakerRepairBot(4, 3, settings, 2);
    }
}
