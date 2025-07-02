package h05.entity;

import h05.equipment.Equipment;
import h05.equipment.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public interface Equipable {

    @DoNotTouch
    Equipment[] getEquipments();

    @DoNotTouch
    UsableEquipment[] getUsableEquipments();

    @DoNotTouch
    void useEquipment(int index);

    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    /**
     * Unequips the equipment at the specified index.
     *
     * @param index the index of the equipment to unequip
     */
    @DoNotTouch
    void unequip(int index);
}
