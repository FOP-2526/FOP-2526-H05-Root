package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A tool called pickaxe which is good for mining resources like rocks.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Pickaxe extends AbstractEquipment implements Tool {

    /**
     * Constructs a pickaxe instance.
     */
    @DoNotTouch
    public Pickaxe() {
        super("Pickaxe");
    }
}
