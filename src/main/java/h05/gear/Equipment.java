package h05.gear;

import h05.Durable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Equipment extends Durable {

    @DoNotTouch
    @NotNull String getName();

    @DoNotTouch
    default @NotNull EquipmentCondition getCondition() {
        int durability = getDurability();
        if (durability > 80) {
            return EquipmentCondition.NEW;
        } else if (durability > 40) {
            return EquipmentCondition.USED;
        } else if (durability > 0) {
            return EquipmentCondition.DAMAGED;
        }
        return EquipmentCondition.BROKEN;
    }

    @DoNotTouch
    boolean isUpgrade();

    @DoNotTouch
    boolean isUsable();

    @DoNotTouch
    boolean isTool();
}
