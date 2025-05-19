package h05;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface UpgradeableEquipment extends Equipment {

    @DoNotTouch
   AttachableEquipment[] getUpgrades();

    @DoNotTouch
    int getNumberOfUpgrades();

    @DoNotTouch
    void attach(@NotNull AttachableEquipment upgrade);

    @DoNotTouch
    void detach(@NotNull AttachableEquipment upgrade);

    @Override
    default boolean isUpgrade() {
        return false;
    }

    @Override
    default boolean isUsable() {
        return false;
    }

    default boolean isTool() {
        return false;
    }
}
