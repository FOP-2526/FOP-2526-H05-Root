package h05;

import h05.equipment.AbstractAttachableEquipment;

public class TelephotoLense extends AbstractAttachableEquipment {

    private final int rangeEnhancement;

    public TelephotoLense(int rangeEnhancement) {
        super("Telephoto Lense");
        this.rangeEnhancement = rangeEnhancement;
    }

    public int getRangeEnhancement() {
        return rangeEnhancement;
    }
}
