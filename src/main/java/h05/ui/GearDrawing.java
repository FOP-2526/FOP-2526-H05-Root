package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entity.Gear;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    @DoNotTouch
    public GearDrawing() {
        super(Equipment.Condition.values().length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        return getImage(entity.getEquipment().getCondition().ordinal());
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Gear> context) {
        Gear entity = context.entity();
        Equipment equipment = entity.getEquipment();
        Equipment.Condition[] conditions = Equipment.Condition.values();
        for (Equipment.Condition condition : conditions) {
            String path = equipment.getName() + "_" + condition.name().toLowerCase() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(condition.ordinal(), image);
        }
    }
}
