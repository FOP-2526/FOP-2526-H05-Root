package h05.equipment;

public class TelephotoLens extends AbstractAttachableEquipment {

    private final int rangeEnhancement;

    public TelephotoLens(int rangeEnhancement) {
        super("TelephotoLens");
        this.rangeEnhancement = rangeEnhancement;
    }

    public int getRangeEnhancement() {
        return rangeEnhancement;
    }
}
