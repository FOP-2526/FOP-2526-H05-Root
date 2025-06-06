package h05.entity;

import fopbot.Block;
import h05.loot.Mineable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

/**
 * Represents a loot block in the game world that can be mined by a miner.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class Loot extends Block {

    /**
     * The mineable resource contained in this loot block.
     */
    @DoNotTouch
    private final @NotNull Mineable mineable;

    /**
     * Constructs and initializes a field entity at the specified {@code (x,y)} location in the
     * coordinate space.
     *
     * @param x the X coordinate of the newly constructed field entity
     * @param y the Y coordinate of the newly constructed field entity
     */
    @DoNotTouch
    public Loot(int x, int y, @NotNull Mineable mineable) {
        super(x, y);
        this.mineable = mineable;
    }

    /**
     * Returns the mineable resource contained in this loot block.
     *
     * @return the mineable resource
     */
    @DoNotTouch
    public @NotNull Mineable getMineable() {
        return mineable;
    }
}
