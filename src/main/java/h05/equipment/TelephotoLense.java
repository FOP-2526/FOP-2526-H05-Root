package h05.equipment;

public class TelephotoLense extends AbstractEquipment {

    private final int rangeEnhancement;

    public TelephotoLense(int rangeEnhancement) {
        super("Telephoto Lense");
        this.rangeEnhancement = rangeEnhancement;
    }

    public int getRangeEnhancement() {
        return rangeEnhancement;
    }

    public int extendVisibilityRange(Camera camera) {
        int visibilityRange = camera.getVisibilityRange();
        reduceDurability(visibilityRange);
        if (getCondition() == Condition.BROKEN) {
            return visibilityRange;
        }
        visibilityRange += rangeEnhancement;
        reduceDurability(rangeEnhancement);
        return visibilityRange;
    }
}
