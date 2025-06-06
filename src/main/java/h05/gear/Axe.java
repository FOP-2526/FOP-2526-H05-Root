package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A tool called pickaxe which is good for mining resources like trees.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Axe extends AbstractEquipment implements Tool {

    /**
     * Constructs an axe instance.
     */
    @DoNotTouch
    public Axe() {
        super("Axe");
    }
}
