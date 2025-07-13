package h05.equipment;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

@StudentCreationRequired("H5.2.4")
public class Pickaxe extends AbstractEquipment implements Tool {

    @DoNotTouch
    public Pickaxe() {
        super("Pickaxe");
    }

    @StudentImplementationRequired
    @Override
    public double getMiningPower() {
        return 15;
    }
}
