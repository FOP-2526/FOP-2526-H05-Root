package h05;

public interface Equipment extends Durable {

    String getName();

    default Condition getCondition() {
        int durability = getDurability();
        if (durability > 80) {
            return Condition.NEW;
        } else if (durability > 40) {
            return Condition.USED;
        } else if (durability > 0) {
            return Condition.DAMAGED;
        }
        return Condition.BROKEN;
    }

    boolean isUpgrade();

    boolean isUsable();

    enum Condition {
        NEW,
        USED,
        DAMAGED,
        BROKEN
    }
}
