package h05;

public class AbstractEquipment extends AbstractDurableObject implements Equipment {

    private final String name;

    public AbstractEquipment(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
