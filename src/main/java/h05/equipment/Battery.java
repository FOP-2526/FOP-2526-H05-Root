package h05.equipment;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class Battery extends AbstractEquipment {

    @DoNotTouch
    public Battery() {
        super("Battery");
    }

    @StudentImplementationRequired
    public void increaseDurability(double value) {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return;
        }
        setDurability(getDurability() + value);
    }
}
