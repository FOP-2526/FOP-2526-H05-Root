package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

/**
 * A telephoto lens is a type of equipment that enhances the range of a camera.
 *
 * @author Nhan Huynh, Nico Schnieders
 * @see Camera
 */
@StudentCreationRequired("H5.2.3")
public class TelephotoLens extends AbstractEquipment implements UsableEquipment {

    /**
     * The range enhancement provided by this telephoto lens, which determines how much further the camera can see.
     */
    private final int rangeEnhancement;

    /**
     * Constructs a new {@link TelephotoLens} instance with the specified range enhancement.
     *
     * @param rangeEnhancement the range enhancement of the telephoto lens, must be greater than 0
     */
    public TelephotoLens(int rangeEnhancement) {
        super("TelephotoLens");
        this.rangeEnhancement = rangeEnhancement;
    }

    /**
     * Returns the range enhancement provided by this telephoto lens, which determines how much further the camera can
     * see.
     *
     * @return the range enhancement of this telephoto lens
     */
    public int getRangeEnhancement() {
        return rangeEnhancement;
    }

    @Override
    public void use(@NotNull Miner miner) {
        if (getCondition() == EquipmentCondition.BROKEN) {
            return;
        }
        int visibilityRange = miner.getCamera().getVisibilityRange();
        miner.getCamera().setVisibilityRange(visibilityRange + rangeEnhancement);
        setDurability(0);
    }
}
