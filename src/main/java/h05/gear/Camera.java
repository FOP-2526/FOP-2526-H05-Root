package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * A camera is a type of equipment that defines the vision of an entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Camera extends AbstractUpgradeableEquipment {

    /**
     * The default visibility range of the camera.
     */
    @DoNotTouch
    public static final int DEFAULT_VISIBILITY_RANGE = 1;

    /**
     * The visibility range of the camera.
     * This defines how far the camera can see.
     */
    @DoNotTouch
    private int visibilityRange;

    /**
     * Constructs a camera with the specified visibility range.
     *
     * @param visibilityRange the visibility range of the camera
     */
    @DoNotTouch
    public Camera(int visibilityRange) {
        super("Camera");
        this.visibilityRange = visibilityRange;
    }

    /**
     * Constructs a camera with the default visibility range.
     */
    @DoNotTouch
    public Camera() {
        this(DEFAULT_VISIBILITY_RANGE);
    }

    /**
     * Returns the visibility range of the camera.
     * If the camera is damaged, the visibility range is 0.
     *
     * @return the visibility range
     */
    @DoNotTouch
    public int getVisibilityRange() {
        if (getCondition() == EquipmentCondition.DAMAGED) {
            return 0;
        }
        return visibilityRange;
    }

    /**
     * Sets the visibility range of the camera.
     * This method is used to adjust the visibility range when attaching or detaching upgrades.
     *
     * @param visibilityRange the new visibility range
     */
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
