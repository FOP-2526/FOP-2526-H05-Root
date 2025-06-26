package h05.game;

import fopbot.FieldEntity;
import fopbot.World;
import h05.entity.Fog;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A basic implementation of the {@link GameSettings} interface.
 */
@DoNotTouch
public final class BasicGameSettings implements GameSettings {

    /**
     * Returns all entities of the specified class located at the given coordinates.
     *
     * @param x     the x-coordinate of the field
     * @param y     the y-coordinate of the field
     * @param clazz the class of the entities to retrieve
     * @param <E>   the type of the entities to retrieve
     *
     * @return an array of entities of the specified class at the given coordinates; an empty array if no such entities
     *     exist
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
     * Returns the first entity of the specified class located at the given coordinates.
     *
     * @param x     the x-coordinate of the field
     * @param y     the y-coordinate of the field
     * @param clazz the class of the entity to retrieve
     * @param <E>   the type of the entity to retrieve
     *
     * @return the first entity of the specified class at the given coordinates, or {@code null} if no such entity
     *     exists
     */
    @DoNotTouch
    private static <E> @Nullable E getEntityAt(int x, int y, Class<E> clazz) {
        E[] entities = getEntitiesAt(x, y, clazz);
        return entities.length > 0 ? entities[0] : null;
    }

    @DoNotTouch
    @Override
    public void placeFog(int x, int y) {
        if (getEntityAt(x, y, Fog.class) != null) {
            return;
        }
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    @DoNotTouch
    @Override
    public void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }
}

