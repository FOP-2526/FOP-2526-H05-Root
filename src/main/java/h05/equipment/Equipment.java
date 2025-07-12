package h05.equipment;

import h05.Durable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public interface Equipment extends Durable {

    @DoNotTouch
    @NotNull
    String getName();

    @StudentImplementationRequired("H5.2.1")
    default @NotNull EquipmentCondition getCondition() {
        final double durability = getDurability();
        if (durability > 80) {
            return EquipmentCondition.NEW;
        } else if (durability > 40) {
            return EquipmentCondition.USED;
        } else if (durability > 0) {
            return EquipmentCondition.DAMAGED;
        }
        return EquipmentCondition.BROKEN;
    }

    boolean isUsable();

    boolean isTool();
}
