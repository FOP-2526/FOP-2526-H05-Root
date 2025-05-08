package h05.mineable;

import h05.AbstractDurableObject;
import h05.Mineable;

public abstract class AbstractMineableObject extends AbstractDurableObject implements Mineable {

    private final String name;

    public AbstractMineableObject(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
