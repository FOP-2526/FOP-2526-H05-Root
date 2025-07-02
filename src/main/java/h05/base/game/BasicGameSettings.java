package h05.base.game;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.Wall;
import fopbot.World;
import h05.base.entity.Fog;
import h05.base.entity.Gear;
import h05.base.entity.Loot;
import h05.entity.Miner;
import h05.equipment.UsableEquipment;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public final class BasicGameSettings implements GameSettings {

    @DoNotTouch
    public BasicGameSettings() {
    }

    @DoNotTouch
    @SuppressWarnings("unchecked")
    private static <E> @NotNull E[] getEntitiesAt(int x, int y, Class<E> clazz) {
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

    @DoNotTouch
    @Override
    public @Nullable Mineable getMineableAt(int x, int y) {
        Loot loot = getEntityAt(x, y, Loot.class);
        return loot != null ? loot.getMineable() : null;
    }

    @DoNotTouch
    @Override
    public @Nullable UsableEquipment getUsableEquipmentAt(int x, int y) {
        Gear gear = getEntityAt(x, y, Gear.class);
        return gear == null ?
            null : gear.getEquipment() instanceof UsableEquipment usableEquipment ?
            usableEquipment : null;
    }

    @DoNotTouch
    @Override
    public @Nullable Miner getMinerAt(int x, int y) {
        return getEntityAt(x, y, Miner.class);
    }

    @DoNotTouch
    @Override
    public @NotNull Wall[] getWallsAt(int x, int y) {
        return getEntitiesAt(x, y, Wall.class);
    }

    @Override
    public void removeEntity(@NotNull FieldEntity entity) {
        World.getGlobalWorld().removeEntity(entity);
    }
}
