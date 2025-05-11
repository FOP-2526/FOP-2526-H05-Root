package h05.equipment;

import h05.Equipment;

public interface Tool extends Equipment {
    default boolean isUpgrade() {
        return false;
    }

    default boolean isUsable() {
        return false;
    }

}
