package h05.entity;

import fopbot.Block;
import fopbot.World;
import h05.Mineable;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Loot extends Block {

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
        World.getGlobalWorld().placeEntity(this);
    }

    @DoNotTouch
    public @NotNull Mineable getMineable() {
        return mineable;
    }
}
