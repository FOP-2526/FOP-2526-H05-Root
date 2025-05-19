package h05;

import fopbot.Direction;
import fopbot.Field;
import fopbot.FieldEntity;
import fopbot.World;
import h05.loot.Rock;
import h05.loot.Tree;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.entity.Loot;
import h05.gear.Axe;
import h05.gear.Battery;
import h05.gear.Pickaxe;
import h05.gear.Tool;

import java.awt.*;
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

    public static boolean isOnGear(int x, int y) {
        return World.getGlobalWorld().getField(x, y).contains(Gear.class);
    }

    public static boolean isGear(FieldEntity entity) {
        return entity instanceof Gear;
    }

    public static boolean isTool(FieldEntity entity) {
        return entity instanceof Gear && ((Gear) entity).getEquipment() instanceof Tool;
    }

    public static FieldEntity[] getEntities(int x, int y) {
        return World.getGlobalWorld().getField(x, y).getEntities().toArray(FieldEntity[]::new);
    }

    public static Loot getLootAtPoint(int x, int y) {
        if (World.getGlobalWorld().getField(x, y).getEntities().isEmpty()) {
            return null;
        }
        FieldEntity entity = World.getGlobalWorld().getField(x, y).getEntities().getFirst();
        if (!(entity instanceof Loot)) {
            return null;
        }
        return (Loot) entity;
    }

    public static void placePrimaryTool(int x, int y, Tool primaryTool) {
        removeTool(x, y);
        World.getGlobalWorld().placeEntity(new Gear(x, y, primaryTool));
    }

    public static Point getPointInFront(int x, int y, Direction dir) {
        int newX = x + dir.getDx();
        int newY = y + dir.getDy();
        if (newX < 0 || newX >= World.getWidth() || newY < 0 || newY >= World.getHeight()) {
            return null;
        }
        return new Point(newX, newY);
    }

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

    public static void removeTool(int x, int y) {
        List<FieldEntity> entity = World.getGlobalWorld().getField(x, y).getEntities().stream().filter(
            WorldUtilities::isTool
        ).toList();
        entity.forEach(e -> World.getGlobalWorld().removeEntity(e));
    }

    public static void removeLoot(Loot entity) {
        World.getGlobalWorld().removeEntity(entity);
    }

    public static boolean mineLoot(Loot loot, Tool primaryTool) {
        Mineable mineable = loot.getMineable();
        switch (primaryTool) {
            case null -> mineable.reduceDurability(5);
            case Axe axe when mineable instanceof Tree -> mineable.reduceDurability(10);
            case Pickaxe pickaxe when mineable instanceof Rock -> mineable.reduceDurability(10);
            default -> {
                mineable.reduceDurability(5);
            }
        }
        int durability = mineable.getDurability();
        if (durability < 100 && durability > 50) {
            mineable.setState(Mineable.State.HALF_MINED);
        } else if (durability <= 50 && durability > 0) {
            mineable.setState(Mineable.State.FULLY_MINED);
        }
        World.getGlobalWorld().getGuiPanel().repaint();
        return durability <= 0;
    }
}
