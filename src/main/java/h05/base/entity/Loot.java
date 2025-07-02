package h05.base.entity;

import fopbot.Block;
import fopbot.World;
import h05.mineable.Mineable;
import h05.Tool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Loot extends Block {

    @DoNotTouch
    private final @NotNull Mineable mineable;
    private final @NotNull Mineable decorator;

    @DoNotTouch
    public Loot(int x, int y, @NotNull Mineable mineable) {
        super(x, y);
        this.mineable = mineable;
        this.decorator = new Mineable() {

            @Override
            public @NotNull String getName() {
                return mineable.getName();
            }

            @SuppressWarnings("UnstableApiUsage")
            @Override
            public boolean onMined(@Nullable Tool tool) {
                boolean isMined = mineable.onMined(tool);
                if (isMined) {
                    World.getGlobalWorld().getGuiPanel().updateGui();
                }
                return isMined;
            }

            @Override
            public double getDurability() {
                return mineable.getDurability();
            }

            @Override
            public void setDurability(double durability) {
                mineable.setDurability(durability);
            }

            @Override
            public void reduceDurability(double amount) {
                mineable.reduceDurability(amount);
            }

            @Override
            public int hashCode() {
                return mineable.hashCode();
            }

            @Override
            public boolean equals(Object other) {
                return this == other || (other instanceof Mineable && mineable.equals(other));
            }

            @Override
            public String toString() {
                return mineable.toString();
            }
        };
    }

    @DoNotTouch
    public @NotNull Mineable getMineable() {
        return mineable;
    }

    public @NotNull Mineable getDecorator() {
        return decorator;
    }
}
