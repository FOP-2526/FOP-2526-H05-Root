package h05;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.World;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.equipment.Tool;
import h05.AbstractMinableEntity.AbstractMinableEntity;

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

    public static boolean isTool(FieldEntity entity) {
        return entity instanceof Tool;
    }

    public static FieldEntity[] getEntities(int x, int y) {
        return World.getGlobalWorld().getField(x, y).getEntities().toArray(FieldEntity[]::new);
    }

    public static AbstractMinableEntity getNode(int x, int y) {
        if (World.getGlobalWorld().getField(x, y).getEntities().isEmpty()) {
            return null;
        }
        return (AbstractMinableEntity) World.getGlobalWorld().getField(x, y).getEntities().getFirst();
    }

    public static Point getPointInFront(int x, int y, Direction dir) {
        int newX = x + dir.getDx();
        int newY = y + dir.getDy();
        if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
            return null;
        }
        return new Point(newX, newY);
    }

    public static class Point {
        public int x;
        public int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
