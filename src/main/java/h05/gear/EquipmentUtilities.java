package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EquipmentUtilities {

    private EquipmentUtilities() {
    }

    public static @Nullable TelephotoLens getAsTelephotoLens(@NotNull Equipment equipment) {
        return equipment instanceof TelephotoLens telephotoLens ? telephotoLens : null;
    }

    public static @Nullable Powerbank getAsPowerbank(@NotNull Equipment equipment) {
        return equipment instanceof Powerbank powerbank ? powerbank : null;
    }
}
