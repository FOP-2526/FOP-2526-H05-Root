package h05.equipment;

import h05.entity.Miner;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.StudentCreationRequired;

@StudentCreationRequired("H5.2.3")
public class TelephotoLens extends AbstractEquipment implements UsableEquipment {

    private final int rangeEnhancement;

    public TelephotoLens(int rangeEnhancement) {
        super("TelephotoLens");
        this.rangeEnhancement = rangeEnhancement;
    }

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
