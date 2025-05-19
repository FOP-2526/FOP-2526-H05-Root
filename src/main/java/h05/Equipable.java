package h05;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Equipable {

    @DoNotTouch
    Equipment[] getEquipmentStorage();

    @DoNotTouch
    UsableEquipment[] getUsableEquipments();

    @DoNotTouch
    int getEquipmentCount();

    @DoNotTouch
    void useEquipment(int index);

    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    @DoNotTouch
    void unequip(int index);
}
