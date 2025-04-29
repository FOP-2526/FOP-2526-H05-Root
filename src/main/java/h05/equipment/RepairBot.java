package h05.equipment;

import fopbot.Robot;
import h05.Repairer;

import java.util.Arrays;

public abstract class RepairBot extends Robot implements Repairer {

    private static final int DEFAULT_CAPACITY = 10;

    private static final double GROWTH_FACTOR = 2;

    private Instruction[] instructions;

    private int next;

    private int durability = 100;

    public RepairBot(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public void reduceDurability(int amount) {
        durability -= amount;
    }

    @Override
    public boolean hasWork() {
        return next > 0;
    }

    @Override
    public Instruction[] getInstructions() {
        return instructions;
    }

    @Override
    public Instruction getInstruction(int index) {
        return instructions[index];
    }

    @Override
    public void assign(Instruction instruction) {
        if (next == instructions.length) {
            instructions = Arrays.copyOf(instructions, (int) Math.round(instructions.length * GROWTH_FACTOR));
        }
        instructions[next++] = instruction;
    }

    @Override
    public void removeInstruction(Instruction instruction) {
        int i = 0;
        boolean removed = false;
        for (; i < next; i++) {
            if (instructions[i] == instruction) {
                removed = true;
                break;
            }
        }
        for (; i < next; i++) {
            instructions[i] = instructions[i - 1];
        }
        if (removed) {
            next--;
        }
    }
}
