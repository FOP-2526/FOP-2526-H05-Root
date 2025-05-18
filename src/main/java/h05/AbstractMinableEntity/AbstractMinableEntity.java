package h05.AbstractMinableEntity;

import fopbot.Block;
import fopbot.World;
import h05.Mineable;
import h05.equipment.Tool;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMinableEntity extends Block implements Mineable {
    private State miningState;
    private int durability;
    private final String name;

    public AbstractMinableEntity(int x, int y, String name) {
        super(x, y);
        this.name = name;
        this.miningState = State.NOT_MINED;
        this.durability = 100;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public int onMined(@NotNull Tool tool) {
        int amountToReduceDurabilityBy = 0;
        if (tool == null) {
            amountToReduceDurabilityBy = 5;
        } else if
        (getName().equals("rock") && tool.getName().equals("pickaxe")) {
            amountToReduceDurabilityBy = 20;
        } else if (getName().equals("tree") && tool.getName().equals("axe")) {
            amountToReduceDurabilityBy = 20;
        }
        reduceDurability(amountToReduceDurabilityBy);
        updateAfterMining();
        return amountToReduceDurabilityBy;
    }

    public void updateAfterMining() {
        if (durability < 100 && durability > 50) {
            setMiningState(State.HALF_MINED);
            World.getGlobalWorld().getGuiPanel().repaint();
        } else if (durability <= 50 && durability > 0) {
            setMiningState(State.FULLY_MINED);
            World.getGlobalWorld().getGuiPanel().repaint();
        } else if (durability <= 0) {
            World.getGlobalWorld().removeEntity(getX(), getY(), getClass());

        }
    }


    public @NotNull State getState() {
        return miningState;
    }

    public void setMiningState(State miningState) {
        this.miningState = miningState;
    }

    public void reduceDurability(int amount) {
        durability -= amount;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
