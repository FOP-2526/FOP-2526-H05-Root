package h05.equipment;

import h05.Tool;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

@StudentCreationRequired
public class Axe extends AbstractEquipment implements Tool {

    @DoNotTouch
    public Axe() {
        super("Axe");
    }

    @StudentImplementationRequired
    @Override
    public double getMiningPower() {
        return 15;
    }
}
