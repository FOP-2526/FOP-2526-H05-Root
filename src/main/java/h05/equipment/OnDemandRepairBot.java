package h05.equipment;

import fopbot.Direction;
import h05.robot.Battery;
import h05.robot.Camera;
import h05.Equipment;
import h05.Repairer;

public class OnDemandRepairBot extends RepairBot {

    public OnDemandRepairBot(int x, int y) {
        super(x, y);
    }

    @Override
    public void work() {
        if (!hasWork()) {
            return;
        }
        Repairer.Instruction instruction = getInstruction(0);
        removeInstruction(instruction);
        EquippedRobot robot = instruction.getRobot();
        move(robot.getX(), robot.getY());
        replaceEquipment(robot, instruction);
    }

    private void replaceEquipment(EquippedRobot robot, Repairer.Instruction instruction) {
        Equipment damaged = null;
        for (Equipment equipment : robot.getEquipments()) {
            if (equipment.getName().equals(instruction.getEquipmentName())) {
                damaged = equipment;
            }
        }
        if (damaged == null) {
            return;
        }
        if (damaged.getCondition() == Equipment.Condition.BROKEN) {
            robot.turnOff();
        } else {
            robot.unequip(damaged);
            if (damaged.getName().equals("Battery")) {
                robot.equip(new Battery());
            } else if (damaged.getName().equals("Camera")) {
                robot.equip(new Camera());
            }
        }
    }

    private void move(int x, int y) {
        if (x < getX()) {
            turn(Direction.RIGHT);
            move(getX() - x);
        } else if (x > getX()) {
            turn(Direction.LEFT);
            move(x - getX());
        }
        if (y < getY()) {
            turn(Direction.UP);
            move(getY() - y);
        } else if (y > getY()) {
            turn(Direction.DOWN);
            move(y - getY());
        }
    }

    private void move(int steps) {
        for (int i = 0; i < steps; i++) {
            move();
        }
    }

    private void turn(Direction direction) {
        while (getDirection() != direction) {
            turnLeft();
        }
    }
}
