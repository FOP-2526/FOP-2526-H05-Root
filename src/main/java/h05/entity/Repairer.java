package h05.entity;

import h05.game.GameSettings;
import h05.game.TickBased;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

/**
 * A repairer is a robot that can repair other robots or entities in the game world.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public interface Repairer extends TickBased {

    /**
     * Provides access to the game settings associated with this repairer.
     *
     * @return the current game settings
     */
    @NotNull GameSettings getGameSettings();

    /**
     * Returns the radius of the repairer, which defines how far it can reach to repair entities.
     *
     * @return the radius of the repairer
     */
    @DoNotTouch
    int getRadius();

    /**
     * Scans the area around the repairer to find an entity that needs repair.
     *
     * @return the position of the entity that needs repair, or {@code null} if no such entity is found
     */
    @DoNotTouch
    @Nullable
    Point scan();

    /**
     * Repairs the entity at the specified point.
     *
     * @param point the position of the entity to repair
     */
    @DoNotTouch
    void repair(@NotNull Point point);
}
