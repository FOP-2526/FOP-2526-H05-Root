package h05.entity;

import fopbot.Block;
import fopbot.World;
import h05.gear.Tool;
import h05.loot.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
     * @param x the x-coordinate of the newly constructed field entity
     * @param y the y-coordinate of the newly constructed field entity
     */
    @DoNotTouch
    public Loot(int x, int y, @NotNull Mineable mineable) {
        super(x, y);
        this.mineable = new Mineable() {
            @Override
            public @NotNull String getName() {
                return mineable.getName();
            }

            @Override
            public boolean onMined(@Nullable Tool tool) {
                boolean isMined = mineable.onMined(tool);
                if (isMined) {
                    World.getGlobalWorld().getGuiPanel().updateGui();
                }
                return isMined;
            }

            @Override
            public int getDurability() {
                return mineable.getDurability();
            }

            @Override
            public void setDurability(int durability) {
                mineable.setDurability(durability);
            }

            @Override
            public void reduceDurability(int amount) {
                mineable.reduceDurability(amount);
            }
        };
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
