package h05.base.entity;

import fopbot.Block;
import fopbot.World;
import h05.equipment.Tool;
import h05.mineable.Mineable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Loot extends Block {

    @DoNotTouch
    private final @NotNull Mineable mineable;
    @DoNotTouch
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
            @DoNotTouch
            public double getDurability() {
                return mineable.getDurability();
            }

            @Override
            @DoNotTouch
            public void setDurability(double durability) {
                mineable.setDurability(durability);
            }

            @Override
            @DoNotTouch
            public void reduceDurability(double amount) {
                mineable.reduceDurability(amount);
            }

            @Override
            @DoNotTouch
            public int hashCode() {
                return mineable.hashCode();
            }

            @Override
            @DoNotTouch
            public boolean equals(Object other) {
                return this == other || (other instanceof Mineable && mineable.equals(other));
            }

            @Override
            @DoNotTouch
            public String toString() {
                return mineable.toString();
            }
        };
    }

    @DoNotTouch
    public @NotNull Mineable getMineable() {
        return mineable;
    }

    @DoNotTouch
    public @NotNull Mineable getDecorator() {
        return decorator;
    }
}
