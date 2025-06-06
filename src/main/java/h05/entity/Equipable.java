package h05.entity;

import h05.gear.Equipment;
import h05.gear.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * An entity that can be equipped with various types of equipment.
 */
@DoNotTouch
public interface Equipable {

    /**
     * Returns the number of equipments that this entity currently has.
     *
     * @return the number of equipments that this entity currently has.
     */
    @DoNotTouch
    Equipment[] getEquipments();

    /**
     * Returns the number of equipments that this entity currently has.
     *
     * @return the number of equipments that this entity currently has
     */
    @DoNotTouch
    int getNumberOfEquipments();

    /**
     * Returns the number of usable equipments that this entity currently has.
     *
     * @return the number of usable equipments that this entity currently has
     */
    @DoNotTouch
    UsableEquipment[] getUsableEquipments();

    /**
     * Uses the equipment at the specified index.
     *
     * @param index the index of the equipment to use
     */
    @DoNotTouch
    void useEquipment(int index);

    /**
     * Equips the specified equipment to this entity. If the entity already has the maximum amount of equipment,
     * no action is taken.
     *
     * @param equipment the equipment to equip
     */
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
