package h05;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.World;
import h05.AbstractMinableEntity.Rock;
import h05.AbstractMinableEntity.Tree;
import h05.entity.Fog;
import h05.entity.Gear;
import h05.equipment.Axe;
import h05.equipment.Battery;
import h05.equipment.Pickaxe;
import h05.equipment.Tool;
import h05.AbstractMinableEntity.AbstractMinableEntity;

import java.awt.*;
import java.util.Random;
import java.util.stream.Collectors;

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

    public static AbstractMinableEntity getMinableEntity(int x, int y) {
        if (World.getGlobalWorld().getField(x, y).getEntities().isEmpty()) {
            return null;
        }
        var entity = World.getGlobalWorld().getField(x, y).getEntities().getFirst();
        if (!(entity instanceof AbstractMinableEntity)) {
            return null;
        }
        return (AbstractMinableEntity) entity;
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
        var fields = World.getGlobalWorld().getFields();
        var emptyFields = fields.stream().filter(field -> field.getEntities().isEmpty()).toList();
        Random random = new Random();
        var randomField = emptyFields.isEmpty() ? null : emptyFields.get(random.nextInt(emptyFields.size()));
        if (randomField == null) {
            return;
        }
        World.getGlobalWorld().placeEntity(new Gear(randomField.getX(), randomField.getY(), new Battery()));
    }

    public static void removeTool(int x, int y) {
        var entity = World.getGlobalWorld().getField(x, y).getEntities().stream().filter(
            WorldUtilities::isTool
        ).toList();
        entity.forEach(e -> World.getGlobalWorld().removeEntity(e));
    }

    public static void removeMinedEntity(FieldEntity entity) {
        World.getGlobalWorld().removeEntity(entity);
    }

    public static boolean mineEntity(AbstractMinableEntity entity, Tool primaryTool) {
        switch (primaryTool) {
            case Axe axe when entity instanceof Tree -> entity.reduceDurability(10);
            case Pickaxe pickaxe when entity instanceof Rock -> entity.reduceDurability(10);
            default -> {
                entity.reduceDurability(5);
            }
        }
        var durability = entity.getDurability();
        if (durability < 100 && durability > 50) {
            entity.setMiningState(Mineable.State.HALF_MINED);
        } else if (durability <= 50 && durability > 0) {
            entity.setMiningState(Mineable.State.FULLY_MINED);
        }
        World.getGlobalWorld().getGuiPanel().repaint();
        return durability <= 0;
    }
}
