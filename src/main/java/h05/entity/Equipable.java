package h05.entity;

import h05.base.game.GameSettings;
import h05.equipment.Equipment;
import h05.equipment.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

public interface Equipable {

    @DoNotTouch
    Equipment[] getEquipments();

    @DoNotTouch
    UsableEquipment[] getUsableEquipments();

    int getNumberOfEquipments();

    @DoNotTouch
    void use(int index);

    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    @DoNotTouch
    void unequip(int index);
}
