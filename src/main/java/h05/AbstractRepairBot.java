package h05;

import fopbot.Robot;

import java.util.Arrays;

public abstract class AbstractRepairBot extends Robot implements Repairer {

    private static final int DEFAULT_CAPACITY = 10;

    private static final int GROWTH_RATE = 2;

    private RepairInstruction[] instructions = new RepairInstruction[DEFAULT_CAPACITY];

    private int next = 0;

    public AbstractRepairBot(int x, int y) {
        super(x, y);
    }

    @Override
    public RepairInstruction[] getInstructions() {
        return instructions;
    }

    @Override
    public void assign(RepairInstruction instruction) {
        if (next == instructions.length) {
            instructions = Arrays.copyOf(instructions, instructions.length * GROWTH_RATE);
        }
        instructions[next++] = instruction;
    }
}
