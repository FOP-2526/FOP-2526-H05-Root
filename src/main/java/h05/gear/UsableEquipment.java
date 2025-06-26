package h05.gear;

import h05.entity.Miner;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a piece of equipment that can be actively used by an entity in a specific direction and at specific
 * coordinates in the game world to perform actions.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface UsableEquipment extends Equipment {

    /**
     * Uses the equipment with the given {@link Miner} as the acting entity.
     *
     * <p>Using the equipment may reduce its durability and therefore change its condition.
     *
     * @param miner the miner who is using the equipment; must not be {@code null}
     */
    void use(Miner miner);
}

