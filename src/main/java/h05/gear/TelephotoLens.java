package h05.gear;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class TelephotoLens extends AbstractEquipment implements AttachableEquipment {

    @DoNotTouch
    private final int rangeEnhancement;

    @DoNotTouch
    public TelephotoLens(int rangeEnhancement) {
        super("TelephotoLens");
        this.rangeEnhancement = rangeEnhancement;
    }

    @DoNotTouch
    public int getRangeEnhancement() {
        return rangeEnhancement;
    }
}
