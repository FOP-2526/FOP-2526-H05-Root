package h05;

import fopbot.FieldEntity;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;

public final class WorldUtilities {

    private WorldUtilities() {
    }

    public static void removeFog(int x, int y) {
        World.getGlobalWorld().removeEntity(x, y, Fog.class);
    }

    public static void placeFog(int x, int y) {
        World.getGlobalWorld().placeEntity(new Fog(x, y));
    }

    public static boolean isOnGear(int x, int y) {
        return World.getGlobalWorld().getField(x, y).contains(Gear.class);
    }

    public static boolean isGear(FieldEntity entity) {
        return entity instanceof Gear;
    }

    public static FieldEntity[] getEntities(int x, int y) {
        return World.getGlobalWorld().getField(x, y).getEntities().toArray(FieldEntity[]::new);
    }
}
