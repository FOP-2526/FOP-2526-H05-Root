package h05.equipment;

public class TelephotoLens extends AbstractAttachableEquipment {

    private final int rangeEnhancement;

    public TelephotoLens(int rangeEnhancement) {
        super("Telephoto Lens");
        this.rangeEnhancement = rangeEnhancement;
    }

    public int getRangeEnhancement() {
        return rangeEnhancement;
    }
}
