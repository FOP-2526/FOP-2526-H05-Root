package h05;

import fopbot.Field;
import fopbot.KarelWorld;
import fopbot.Wall;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.entity.Miner;
import h05.gear.Battery;
import h05.gear.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.List;
import java.util.Random;

public final class WorldUtilities {

    private WorldUtilities() {
    }

    public static void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }

    public static void placeFog(int x, int y) {
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    private static <E> @Nullable E getEntityAt(int x, int y, Class<E> clazz) {
        return World.getGlobalWorld().getField(x, y).getEntities()
            .stream()
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findFirst()
            .orElse(null);
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
        return World.getGlobalWorld().getField(x, y).getEntities()
            .stream()
            .filter(Wall.class::isInstance)
            .map(Wall.class::cast)
            .toArray(Wall[]::new);
    }

    @DoNotTouch
    public static void placePrimaryTool(int x, int y, @NotNull Tool primaryTool) {
        removeTool(x, y);
        World.getGlobalWorld().placeEntity(new Gear(x, y, primaryTool));
    }

    @DoNotTouch
    public static void removeTool(int x, int y) {
        KarelWorld world = World.getGlobalWorld();
        world.getField(x, y).getEntities()
            .stream()
            .filter(Tool.class::isInstance)
            .forEach(world::removeEntity);
    }

    @DoNotTouch
    public static void placeNewBattery() {
        List<Field> fields = World.getGlobalWorld().getFields();
        List<Field> emptyFields = fields.stream().filter(field -> field.getEntities().isEmpty()).toList();
        Random random = new Random();
        Field randomField = emptyFields.isEmpty() ? null : emptyFields.get(random.nextInt(emptyFields.size()));
        if (randomField == null) {
            return;
        }
        World.getGlobalWorld().placeEntity(new Gear(randomField.getX(), randomField.getY(), new Battery()));
    }
}
