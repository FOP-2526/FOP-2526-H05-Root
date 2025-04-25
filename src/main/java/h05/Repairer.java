package h05;

public interface Repairer {

    RepairInstruction[] getInstructions();

    void assign(RepairInstruction instruction);

    void repair();
}
