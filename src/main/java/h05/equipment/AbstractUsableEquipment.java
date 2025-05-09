package h05.equipment;

import h05.UsableEquipment;

public abstract class AbstractUsableEquipment extends AbstractEquipment implements UsableEquipment {

    public AbstractUsableEquipment(String name) {
        super(name);
    }

    @Override
    public boolean isUpgrade() {
        return false;
    }

    @Override
    public boolean isUsable() {
        return true;
    }
}
