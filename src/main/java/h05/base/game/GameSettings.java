package h05.base.game;

import fopbot.FieldEntity;
import fopbot.Wall;
import h05.entity.Miner;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Equipment;
import h05.equipment.Tool;
import h05.equipment.UsableEquipment;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface GameSettings {

    @DoNotTouch
    void placeFog(int x, int y);

    @DoNotTouch
    void removeFog(int x, int y);

    @Nullable Battery toBattery(Equipment equipment);

    @Nullable Camera toCamery(Equipment equipment);

    @Nullable Tool toTool(Equipment equipment);

    @Nullable UsableEquipment toUsableEquipment(Equipment equipment);

    @Nullable Mineable getMineableAt(int x, int y);

    @Nullable UsableEquipment getUsableEquipmentAt(int x, int y);

    @Nullable Equipment getAndRemoveGearAt(int x, int y);

    @Nullable Mineable getLootAt(int x, int y);

    @Nullable Miner getMinerAt(int x, int y);

    default @Nullable Wall getWallAt(int x, int y, boolean horizontal) {
        final Wall[] walls = getWallsAt(x, y);
        for (Wall wall : walls) {
            if (wall.isHorizontal() == horizontal) {
                return wall;
            }
        }
        return null;
    }

    @NotNull Wall[] getWallsAt(int x, int y);

    void removeEntity(@NotNull FieldEntity entity);

    void update();
}
