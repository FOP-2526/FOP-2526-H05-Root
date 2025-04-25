package h05;

public interface RobotEquipment extends Durable {

    String getName();

    Condition getCondition();

    enum Condition {
        NEW,
        USED,
        DAMAGED,
        BROKEN
    }
}
