package h05.gear;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for equipment-related operations.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public final class EquipmentUtilities {

    /**
     * Don't let anyone instantiate this class.
     */
    private EquipmentUtilities() {
    }

    /**
     * Returns the given equipment as a TelephotoLens if it is an instance of TelephotoLens,
     * otherwise returns {@code null}.
     *
     * @param equipment the equipment to check
     *
     * @return the equipment as a TelephotoLens or {@code null} if it is not a Tool
     */
    public static @Nullable TelephotoLens getAsTelephotoLens(@NotNull Equipment equipment) {
        return equipment instanceof TelephotoLens telephotoLens ? telephotoLens : null;
    }

    /**
     * Returns the given equipment as a Powerbank if it is an instance of Powerbank, otherwise returns {@code null}.
     *
     * @param equipment the equipment to check
     *
     * @return the equipment as a Powerbank or {@code null} if it is not a Tool
     */
    public static @Nullable Powerbank getAsPowerbank(@NotNull Equipment equipment) {
        return equipment instanceof Powerbank powerbank ? powerbank : null;
    }
}
