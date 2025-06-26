package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A tool is a type of equipment used to mine resources.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Tool extends Equipment {

    /**
     * Returns the mining power of this tool.
     *
     * @return the mining power of this tool
     */
    int getMiningPower();
}
