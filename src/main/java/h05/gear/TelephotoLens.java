package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A telephoto lens is a type of attachable equipment that enhances the range of a camera.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class TelephotoLens extends AbstractAttachableEquipment implements AttachableEquipment {

    /**
     * The range enhancement provided by the telephoto lens.
     */
    @DoNotTouch
    private final int rangeEnhancement;

    /**
     * Constructs a telephoto lens with the specified range enhancement.
     *
     * @param rangeEnhancement the range enhancement provided by the telephoto lens
     */
    @DoNotTouch
    public TelephotoLens(int rangeEnhancement) {
        super("TelephotoLens", "Camera");
        this.rangeEnhancement = rangeEnhancement;
    }

    /**
     * Returns the range enhancement provided by the telephoto lens.
     *
     * @return the range enhancement
     */
    @DoNotTouch
    public int getRangeEnhancement() {
        return rangeEnhancement;
    }
}
