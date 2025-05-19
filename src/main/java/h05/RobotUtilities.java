package h05;

import fopbot.FieldEntity;
import h05.entity.Gear;
import h05.gear.Equipment;
import h05.gear.Tool;
import h05.gear.UsableEquipment;
import org.jetbrains.annotations.Nullable;

public final class RobotUtilities {

    private RobotUtilities() {
    }

    public static @Nullable UsableEquipment getAsUsableEquipment(Equipment equipment) {
        return equipment instanceof UsableEquipment ? (UsableEquipment) equipment : null;
    }

    public static @Nullable Tool getAsTool(Equipment equipment) {
        return equipment instanceof Tool ? (Tool) equipment : null;
    }

    public static @Nullable Gear getAsGear(FieldEntity entity) {
        return entity instanceof Gear ? (Gear) entity : null;
    }
}
