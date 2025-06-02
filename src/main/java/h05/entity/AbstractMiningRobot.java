package h05.entity;

import fopbot.FieldEntity;
import fopbot.KarelWorld;
import fopbot.Wall;
import fopbot.World;
import h05.WorldUtilities;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Equipment;
import h05.gear.Tool;
import h05.loot.BasicInventory;
import h05.loot.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractMiningRobot extends EquippedRobot implements Miner {

    private Battery battery;
    private Camera camera;
    private @Nullable Tool tool;

    private final Inventory inventory;

    public AbstractMiningRobot(int x, int y, int equipmentCapacity, int inventoryCapacity) {
        super(x, y, equipmentCapacity);
        equip(new Battery());
        equip(new Camera());
        equipmentCount += 2;
        this.inventory = new BasicInventory(inventoryCapacity, BasicInventory.DEFAULT_SIZE);
    }

    @Override
    public @NotNull Camera getCamera() {
        return camera;
    }

    @Override
    public @NotNull Battery getBattery() {
        return battery;
    }

    @Override
    public @Nullable Tool getTool() {
        return tool;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @Override
    public void equip(@NotNull Equipment equipment) {
        if (equipment.isTool()) {
            Tool oldTool = tool;
            tool = (Tool) equipment;
            if (oldTool != null && !oldTool.getName().equals(tool.getName())) {
                WorldUtilities.placePrimaryTool(getX(), getY(), oldTool);
            }
        } else if (equipment.getName().equals("Battery")) {
            battery = (Battery) equipment;
            setEquipment(0, battery);
        } else if (equipment.getName().equals("Camera")) {
            camera = (Camera) equipment;
            setEquipment(1, camera);
        } else {
            super.equip(equipment);
        }
    }

    @Override
    public void pickGear() {
        KarelWorld world = World.getGlobalWorld();
        for (FieldEntity entity : world.getField(getX(), getY()).getEntities()) {
            if (entity instanceof Gear gear) {
                world.removeEntity(gear);
                equip(gear.getEquipment());
                return;
            }
        }
    }

    public boolean isWallInFront() {
        int x = getX();
        int y = getY();
        KarelWorld world = World.getGlobalWorld();
        List<FieldEntity> entities = world.getField(x, y).getEntities();
        for (FieldEntity entity : entities) {
            if (entity instanceof Wall e && e.isHorizontal() == getDirection().isHorizontal()) {
                return true;
            }
        }
        return false;
    }
}
