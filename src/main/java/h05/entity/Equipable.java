package h05.entity;

import h05.gear.Equipment;
import h05.gear.UsableEquipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents an entity that can be equipped with various types of {@link Equipment}.
 *
 * <p>Typical examples include characters, units, or machines that can carry
 * and use tools, weapons, or other gear. This interface provides methods to manage
 * the equipment slots of such an entity, including equipping, unequipping, and using
 * equipment.
 *
 * <p>Some equipment may be {@link UsableEquipment}, allowing the entity to actively use them.
 */
@DoNotTouch
public interface Equipable {

    /**
     * Returns the maximum number of equipment slots available for this entity.
     * @return the maximum number of equipment slots
     */
    int capacity();
    /**
     * Returns an array containing all equipment currently equipped by this entity.
     *
     * @return a non-null array of currently equipped {@link Equipment} objects
     */
    @DoNotTouch
    Equipment[] getEquipments();

    /**
     * Returns the total number of equipment items currently equipped by this entity.
     *
     * @return the number of equipped items
     */
    @DoNotTouch
    int numberOfEquipments();

    /**
     * Returns an array containing all {@link UsableEquipment} currently equipped by this entity.
     *
     * @return a non-null array of {@link UsableEquipment} currently equipped
     */
    @DoNotTouch
    UsableEquipment[] getUsableEquipments();

    /**
     * Uses the usable equipment at the given index.
     *
     * @param index the index of the equipment to use
     */
    @DoNotTouch
    void useEquipment(int index);

    /**
     * Equips the specified {@link Equipment} to this entity.
     *
     * <p>If the entity already has the maximum allowed number of equipment items,
     * the method does nothing.
     *
     * @param equipment the equipment to equip; must not be {@code null}
     */
    @DoNotTouch
    void equip(@NotNull Equipment equipment);

    /**
     * Unequips the equipment located at the specified index.
     *
     * @param index the index of the equipment to remove
     */
    @DoNotTouch
    void unequip(int index);
}

