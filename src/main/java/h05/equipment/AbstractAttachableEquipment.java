package h05.equipment;

import h05.AttachableEquipment;

public abstract class AbstractAttachableEquipment extends AbstractEquipment implements AttachableEquipment {

    public AbstractAttachableEquipment(String name) {
        super(name);
    }

    @Override
    public boolean isUpgrade() {
        return true;
    }

    @Override
    public boolean isUsable() {
        return false;
    }
}
