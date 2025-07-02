package h05.equipment;

import h05.Tool;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

@StudentCreationRequired
public class Pickaxe extends AbstractEquipment implements Tool {

    @DoNotTouch
    public Pickaxe() {
        super("Pickaxe");
    }

    @StudentImplementationRequired
    @Override
    public double getMiningPower() {
        return 30;
    }
}
