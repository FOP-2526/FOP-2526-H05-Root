package h05;

import fopbot.FieldEntity;
import h05.entity.Gear;
import h05.equipment.Tool;

public final class RobotUtilities {
    private RobotUtilities() {
    }

    public static UsableEquipment getAsUsableEquipment(Equipment equipment) {
        return equipment instanceof UsableEquipment ? (UsableEquipment) equipment : null;
    }

    public static Tool getAsTool(Equipment equipment) {
        return equipment instanceof Tool ? (Tool) equipment : null;
    }

    public static Gear getAsGear(FieldEntity entity) {
        return entity instanceof Gear ? (Gear) entity : null;
    }
}
