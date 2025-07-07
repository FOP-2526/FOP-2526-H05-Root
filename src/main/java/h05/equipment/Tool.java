package h05.equipment;

public interface Tool extends Equipment {

    double getMiningPower();

    @Override
    default boolean isUsable() {
        return false;
    }

    @Override
    default boolean isTool() {
        return true;
    }
}
