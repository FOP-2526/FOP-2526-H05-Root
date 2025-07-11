package h05.base.game;

import fopbot.FieldEntity;
import fopbot.Wall;
import h05.entity.Miner;
import h05.equipment.*;
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

    @DoNotTouch
    @Nullable Battery toBattery(Equipment equipment);

    @DoNotTouch
    @Nullable Camera toCamery(Equipment equipment);

    @DoNotTouch
    @Nullable Tool toTool(Equipment equipment);

    @DoNotTouch
    @Nullable UsableEquipment toUsableEquipment(Equipment equipment);

    @DoNotTouch
    @Nullable Mineable getMineableAt(int x, int y);

    @DoNotTouch
    @Nullable UsableEquipment getUsableEquipmentAt(int x, int y);

    @DoNotTouch
    @Nullable Equipment getAndRemoveGearAt(int x, int y);

    @DoNotTouch
    @Nullable Mineable getLootAt(int x, int y);

    @DoNotTouch
    @Nullable Miner getMinerAt(int x, int y);

    @DoNotTouch
    default @Nullable Wall getWallAt(int x, int y, boolean horizontal) {
        final Wall[] walls = getWallsAt(x, y);
        for (Wall wall : walls) {
            if (wall.isHorizontal() == horizontal) {
                return wall;
            }
        }
        return null;
    }

    @DoNotTouch
    @NotNull Wall[] getWallsAt(int x, int y);

    @DoNotTouch
    void removeEntity(@NotNull FieldEntity entity);

    @DoNotTouch
    void update();
}
