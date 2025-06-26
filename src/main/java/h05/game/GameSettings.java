package h05.game;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Defines game-specific settings and actions that affect the game environment.
 *
 * <p>This interface provides methods to manipulate the visibility of specific
 * areas or tiles within the game world, such as placing or removing fog of war.
 *
 * <p>Typical use cases include strategy or exploration games where areas of the map
 * are hidden until explored by the player.
 */
@DoNotTouch
public interface GameSettings {

    /**
     * Applies fog to the specified coordinates in the game world, making the area temporarily hidden.
     *
     * @param x the x-coordinate of the field to be hidden
     * @param y the y-coordinate of the field to be hidden
     */
    @DoNotTouch
    void placeFog(int x, int y);

    /**
     * Removes fog from the specified coordinates in the game world, revealing the area to the player.
     *
     * @param x the x-coordinate of the field to be revealed
     * @param y the y-coordinate of the field to be revealed
     */
    @DoNotTouch
    void removeFog(int x, int y);
}

