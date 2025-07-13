package h05.equipment;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

@StudentCreationRequired("H5.2.4")
public class Axe extends AbstractEquipment implements Tool {

    @DoNotTouch
    public Axe() {
        super("Axe");
    }

    @StudentImplementationRequired
    @Override
    public double getMiningPower() {
        return 5;
    }
}
