package h05.entity;

import fopbot.Direction;
import fopbot.FieldEntity;
import fopbot.KarelWorld;
import fopbot.World;
import h05.WorldUtilities;
import h05.gear.AttachableEquipment;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Equipment;
import h05.gear.Tool;
import h05.gear.UpgradeableEquipment;
import h05.loot.BasicInventory;
import h05.loot.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An abstract implementation of a mining robot that can mine resources in the game world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public abstract class AbstractMiningRobot extends EquippedRobot implements Miner {

    /**
     * The battery of the mining robot, which provides power for its operations.
     */
    @DoNotTouch
    private Battery battery;

    /**
     * The camera of the mining robot, which allows it to see the world around it.
     */
    @DoNotTouch
    private Camera camera;

    /**
     * The tool of the mining robot, which is used for mining resources.
     * It can be {@code null} if the robot does not have a tool equipped.
     */
    @DoNotTouch
    private @Nullable Tool tool;

    /**
     * The inventory of the mining robot, which is used to store mineable items.
     */
    @DoNotTouch
    private final Inventory inventory;

    /**
     * Constructs a new {@link AbstractMiningRobot} instance with the specified position, equipment capacity, and
     * inventory capacity.
     *
     * @param x                 the x-coordinate of the mining robot
     * @param y                 the y-coordinate of the mining robot
     * @param equipmentCapacity the capacity for the equipment of the mining robot
     * @param inventoryCapacity the capacity for the inventory of the mining robot
     */
    @DoNotTouch
    public AbstractMiningRobot(int x, int y, int equipmentCapacity, int inventoryCapacity) {
        super(x, y, equipmentCapacity);
        equip(new Battery());
        equip(new Camera());
        equipmentCount += 2;
        this.inventory = new BasicInventory(inventoryCapacity, BasicInventory.DEFAULT_SIZE);
    }

    @DoNotTouch
    @Override
    public @NotNull Camera getCamera() {
        return camera;
    }

    @DoNotTouch
    @Override
    public @NotNull Battery getBattery() {
        return battery;
    }

    @DoNotTouch
    @Override
    public @Nullable Tool getTool() {
        return tool;
    }

    @DoNotTouch
    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    @DoNotTouch
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
            if (equipment.isUpgrade()) {
                AttachableEquipment attachableEquipment = (AttachableEquipment) equipment;
                String parent = attachableEquipment.getParent();
                for (Equipment e : getEquipments()) {
                    if (e.getName().equals(parent)) {
                        ((UpgradeableEquipment) e).attach(attachableEquipment);
                    }
                }
            }
            super.equip(equipment);
        }
    }

    @DoNotTouch
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

    /**
     * Returns {@code true} if there is a wall in front of the robot, {@code false} otherwise.
     *
     * @return {@code true} if there is a wall in front of the robot, {@code false} otherwise
     */
    public boolean isWallInFront() {
        int x = getX();
        int y = getY();
        KarelWorld world = World.getGlobalWorld();
        Direction direction = getDirection();
        switch (direction) {
            case LEFT:
                x = x - 1;
                break;
            case DOWN:
                y = y - 1;
        }
        return WorldUtilities.getWallsAtPoint(x, y).length != 0;
    }
}
