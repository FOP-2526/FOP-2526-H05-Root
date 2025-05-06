package h05;

import h05.robot.EquippedRobot;

public interface Repairer extends Worker {

    Instruction[] getInstructions();

    Instruction getInstruction(int index);

    void assign(Instruction instruction);

    void removeInstruction(Instruction instruction);

    interface Instruction {

        EquippedRobot getRobot();

        String getEquipmentName();
    }
}
