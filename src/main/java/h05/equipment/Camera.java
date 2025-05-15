package h05.equipment;

import h05.AttachableEquipment;
import h05.TelephotoLense;

public class Camera extends AbstractUpgradeableEquipment {

    public static final int DEFAULT_VISIBILITY_RANGE = 1;

    private int visibilityRange;

    public Camera(int visibilityRange) {
        super("Camera");
        this.visibilityRange = visibilityRange;
    }

    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    public int getVisibilityRange() {
        if (getCondition() == Condition.DAMAGED) {
            return 0;
        }
        return visibilityRange;
    }

    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = visibilityRange;
    }

    @Override
    public void attach(AttachableEquipment upgrade) {
        super.attach(upgrade);
        if (upgrade.getName().equals("TelephotoLense")) {
            TelephotoLense lense = (TelephotoLense) upgrade;
            setVisibilityRange(getVisibilityRange() + lense.getRangeEnhancement());
        }
    }

    @Override
    public void detach(AttachableEquipment upgrade) {
        super.detach(upgrade);
        if (upgrade.getName().equals("TelephotoLense")) {
            TelephotoLense lense = (TelephotoLense) upgrade;
            setVisibilityRange(getVisibilityRange() - lense.getRangeEnhancement());
        }
    }

    @Override
    public void reduceDurability(int amount) {
        super.reduceDurability(amount);
        for (AttachableEquipment upgrade : getUpgrades()) {
            if (upgrade.getName().equals("TelephotoLense")) {
                // TODO: Do not expose cast
                TelephotoLense lense = (TelephotoLense) upgrade;
                int range = lense.getRangeEnhancement();
                if (lense.getDurability() <= range) {
                    lense.reduceDurability(range);
                    visibilityRange -= range;
                } else if (lense.getCondition() == Condition.BROKEN) {
                    continue;
                }
                lense.reduceDurability(range);
            }
        }
    }
}
