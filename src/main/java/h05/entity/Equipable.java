package h05.entity;

import h05.gear.Equipment;
import h05.gear.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Equipable {

    @DoNotTouch
    Equipment[] getEquipments();

    @DoNotTouch
    int getNumberOfEquipments();

    @DoNotTouch
    UsableEquipment[] getUsableEquipments();

    @DoNotTouch
    void useEquipment(int index);

    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    @DoNotTouch
    void unequip(int index);
}
