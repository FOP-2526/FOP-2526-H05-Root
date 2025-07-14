package h05.equipment;

/**
 * A tool represents a piece of equipment that can be used for mining.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
public interface Tool extends Equipment {

    /**
     * Returns the mining power of this tool.
     *
     * @return the mining power of this tool
     */
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
