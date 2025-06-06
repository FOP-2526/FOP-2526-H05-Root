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

/**
 * Utility class for managing entities in the world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public final class WorldUtilities {

    /**
     * Don't let anyone instantiate this class.
     */
    @DoNotTouch
    private WorldUtilities() {
    }

    /**
     * Removes the fog at the specified coordinates.
     *
     * @param x the x-coordinate of the fog to remove
     * @param y the y-coordinate of the fog to remove
     */
    @DoNotTouch
    public static void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }

    /**
     * Places a fog at the specified coordinates if there is no fog already present.
     *
     * @param x the x-coordinate to place the fog
     * @param y the y-coordinate to place the fog
     */
    @DoNotTouch
    public static void placeFog(int x, int y) {
        if (getEntityAt(x, y, Fog.class) != null) {
            return;
        }
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    /**
     * Returns the entities of the specified class at the given coordinates, or an empty array if no such entities
     *
     * @param x     the x-coordinate of the entity
     * @param y     the y-coordinate of the entity
     * @param clazz the class of the entity to find
     * @param <E>   the type of the entity to find
     *
     * @return the entities of the specified class at the given coordinates, or an empty array if no such entities exist
     */
    @DoNotTouch
    @SuppressWarnings("unchecked")
    private static <E> @Nullable E[] getEntitiesAt(int x, int y, Class<E> clazz) {
        int length = 0;
        for (FieldEntity entity : World.getGlobalWorld().getField(x, y).getEntities()) {
            if (clazz.isInstance(entity)) {
                length++;
            }
        }

        E[] entities = (E[]) new Object[length];
        int index = 0;

        for (FieldEntity entity : World.getGlobalWorld().getField(x, y).getEntities()) {
            if (clazz.isInstance(entity)) {
                entities[index++] = clazz.cast(entity);
            }
        }
        return entities;
    }

    /**
     * Returns the first entity of the specified class at the given coordinates or {@code null} if no such entity
     * exists.
     *
     * @param x     the x-coordinate of the entity
     * @param y     the y-coordinate of the entity
     * @param clazz the class of the entity to find
     * @param <E>   the type of the entity to find
     *
     * @return the first entity of the specified class at the given coordinates, or {@code null} if no such entity
     *     exists
     */
    private static <E> @Nullable E getEntityAt(int x, int y, Class<E> clazz) {
        E[] entities = getEntitiesAt(x, y, clazz);
        return entities.length > 0 ? entities[0] : null;
    }

    /**
     * Returns the first {@link Miner} at the given coordinates or {@code null} if no such entity exists.
     *
     * @param x the x-coordinate of the miner
     * @param y the y-coordinate of the miner
     *
     * @return the first {@link Miner} at the given coordinates, or {@code null} if no such entity exists
     */
    @DoNotTouch
    public static @Nullable Miner getMinerAt(int x, int y) {
        return getEntityAt(x, y, Miner.class);
    }

    /**
     * Returns the first {@link Loot} at the given coordinates or {@code null} if no such entity exists.
     *
     * @param x the x-coordinate of the loot
     * @param y the y-coordinate of the loot
     *
     * @return the first {@link Loot} at the given coordinates, or {@code null} if no such entity exists
     */
    @DoNotTouch
    public static Loot getLootAt(int x, int y) {
        return getEntityAt(x, y, Loot.class);
    }

    /**
     * Returns an array of all {@link Wall} entities at the specified coordinates.
     *
     * @param x the x-coordinate of the walls
     * @param y the y-coordinate of the walls
     *
     * @return an array of all {@link Wall} entities at the specified coordinates
     */
    @DoNotTouch
    public static Wall[] getWallsAtPoint(int x, int y) {
        return getEntitiesAt(x, y, Wall.class);
    }

    /**
     * Places a primary tool at the specified coordinates, removing any existing tool at that location.
     *
     * @param x           the x-coordinate to place the tool
     * @param y           the y-coordinate to place the tool
     * @param primaryTool the tool to place at the specified coordinates
     */
    @DoNotTouch
    public static void placePrimaryTool(int x, int y, @NotNull Tool primaryTool) {
        removeTool(x, y);
        World.getGlobalWorld().placeEntity(new Gear(x, y, primaryTool));
    }

    /**
     * Removes any tool at the specified coordinates.
     *
     * @param x the x-coordinate of the tool to remove
     * @param y the y-coordinate of the tool to remove
     */
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
