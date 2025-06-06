package h05.entity;

import fopbot.FieldEntity;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a fog entity in the game world that obscures visibility.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Fog extends FieldEntity {

    /**
     * Constructs and initializes a fog entity at the specified {@code (x,y)} location in the
     * coordinate space.
     *
     * @param x the X coordinate of the newly constructed fog entity
     * @param y the Y coordinate of the newly constructed fog entity
     */
    @DoNotTouch
    public Fog(int x, int y) {
        super(x, y);
    }
}
