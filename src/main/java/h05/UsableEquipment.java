package h05;

import fopbot.Direction;

public interface UsableEquipment extends Equipment {

    void use(int x, int y, Direction direction);
}
