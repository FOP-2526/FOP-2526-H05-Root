package h05.entity;

import fopbot.FieldEntity;
import h05.gear.Equipment;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public class Gear extends FieldEntity {

    @DoNotTouch
    private final @NotNull Equipment equipment;

    @DoNotTouch
    public Gear(int x, int y, @NotNull Equipment equipment) {
        super(x, y);
        this.equipment = equipment;
    }

    public @NotNull Equipment getEquipment() {
        return equipment;
    }
}
