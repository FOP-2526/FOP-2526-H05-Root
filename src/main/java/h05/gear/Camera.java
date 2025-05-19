package h05.gear;

import h05.AttachableEquipment;
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
        if (getCondition() == Condition.DAMAGED) {
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
            // TODO: Do not expose cast
            TelephotoLens lense = (TelephotoLens) upgrade;
            setVisibilityRange(getVisibilityRange() + lense.getRangeEnhancement());
        }
    }

    @Override
    public void detach(@NotNull AttachableEquipment upgrade) {
        super.detach(upgrade);
        if (upgrade.getName().equals("TelephotoLens")) {
            // TODO: Do not expose cast
            TelephotoLens lense = (TelephotoLens) upgrade;
            setVisibilityRange(getVisibilityRange() - lense.getRangeEnhancement());
        }
    }

    @Override
    public void reduceDurability(int amount) {
        super.reduceDurability(amount);
        for (AttachableEquipment upgrade : getUpgrades()) {
            if (upgrade.getName().equals("TelephotoLens")) {
                // TODO: Do not expose cast
                TelephotoLens lense = (TelephotoLens) upgrade;
                if (lense.getCondition() == Condition.BROKEN) {
                    continue;
                }
                int range = lense.getRangeEnhancement();
                if (lense.getDurability() <= range) {
                    visibilityRange -= range;
                }
                lense.reduceDurability(range);
            }
        }
    }
}
