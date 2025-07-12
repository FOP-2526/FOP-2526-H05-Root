package h05.entity;

import h05.equipment.Equipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public interface Equipable {

    @DoNotTouch
    Equipment[] getEquipments();

    int getNumberOfEquipments();

    @DoNotTouch
    void use(int index);

    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    @DoNotTouch
    void unequip(int index);
}
