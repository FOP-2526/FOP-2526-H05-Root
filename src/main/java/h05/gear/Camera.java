package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Camera extends AbstractUpgradeableEquipment {

    @DoNotTouch
    public static final int DEFAULT_VISIBILITY_RANGE = 1;

    @DoNotTouch
    private int visibilityRange;

    @DoNotTouch
    public Camera(int visibilityRange) {
        super("Camera");
        this.visibilityRange = visibilityRange;
    }

    @DoNotTouch
    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    @DoNotTouch
    public int getVisibilityRange() {
        if (getCondition() == EquipmentCondition.DAMAGED) {
            return 0;
        }
        return visibilityRange;
    }

    @DoNotTouch
    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = visibilityRange;
    }

    @DoNotTouch
    @Override
    public void attach(@NotNull AttachableEquipment upgrade) {
        super.attach(upgrade);
        if (upgrade.getName().equals("TelephotoLens")) {
            TelephotoLens lens = EquipmentUtilities.getAsTelephotoLens(upgrade);
            setVisibilityRange(getVisibilityRange() + lens.getRangeEnhancement());
        }
    }

    @Override
    public void detach(@NotNull AttachableEquipment upgrade) {
        super.detach(upgrade);
        if (upgrade.getName().equals("TelephotoLens")) {
            TelephotoLens lens = EquipmentUtilities.getAsTelephotoLens(upgrade);
            setVisibilityRange(getVisibilityRange() - lens.getRangeEnhancement());
        }
    }

    @Override
    public void reduceDurability(int amount) {
        super.reduceDurability(amount);
        for (AttachableEquipment upgrade : getUpgrades()) {
            if (upgrade.getName().equals("TelephotoLens")) {
                TelephotoLens lens = EquipmentUtilities.getAsTelephotoLens(upgrade);
                if (lens.getCondition() == EquipmentCondition.BROKEN) {
                    continue;
                }
                int range = lens.getRangeEnhancement();
                if (lens.getDurability() <= range) {
                    visibilityRange -= range;
                }
                lens.reduceDurability(range);
            }
        }
    }
}
