package h05;

import fopbot.FieldEntity;
import fopbot.KarelWorld;
import fopbot.Wall;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.Miner;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Iterator;

@DoNotTouch
public final class WorldUtilities {

    @DoNotTouch
    private WorldUtilities() {
    }

    @DoNotTouch
    public static void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }

    @DoNotTouch
    public static void placeFog(int x, int y) {
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    @DoNotTouch
    private static <E> @Nullable E getEntityAt(int x, int y, Class<E> clazz) {
        for (FieldEntity entity : World.getGlobalWorld().getField(x, y).getEntities()) {
            if (clazz.isInstance(entity)) {
                return clazz.cast(entity);
            }
        }
        return null;
    }

    @DoNotTouch
    public static @Nullable Miner getMinerAt(int x, int y) {
        return getEntityAt(x, y, Miner.class);
    }

    @DoNotTouch
    public static Loot getLootAt(int x, int y) {
        return getEntityAt(x, y, Loot.class);
    }

    @DoNotTouch
    public static Wall[] getWallsAtPoint(int x, int y) {
        int length = 0;
        for (FieldEntity entity : World.getGlobalWorld().getField(x, y).getEntities()) {
            if (entity instanceof Wall wall) {
                length++;
            }
        }

        Wall[] walls = new Wall[length];
        int index = 0;

        for (FieldEntity entity : World.getGlobalWorld().getField(x, y).getEntities()) {
            if (entity instanceof Wall wall) {
                walls[index++] = wall;
            }
        }
        return walls;
    }

    @DoNotTouch
    public static void placePrimaryTool(int x, int y, @NotNull Tool primaryTool) {
        removeTool(x, y);
        World.getGlobalWorld().placeEntity(new Gear(x, y, primaryTool));
    }

    @DoNotTouch
    public static void removeTool(int x, int y) {
        KarelWorld world = World.getGlobalWorld();
        Iterator<FieldEntity> iterator = world.getField(x, y).getEntities().iterator();
        while (iterator.hasNext()) {
            FieldEntity entity = iterator.next();
            if (entity instanceof Gear gear && gear.getEquipment() instanceof Tool) {
                iterator.remove();
            }
        }
    }
}
