package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entity.Gear;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.*;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    @DoNotTouch
    public GearDrawing() {
        super(Equipment.Condition.values().length);
    }

    @Override
    protected void loadImages(int i, DrawingContext<Gear> drawingContext) {
        Gear entity = drawingContext.entity();
        Equipment equipment = entity.getEquipment();
        Equipment.Condition[] conditions = Equipment.Condition.values();
        for (Equipment.Condition condition : conditions) {
            String path = equipment.getName() + "_" + condition.name().toLowerCase() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    i
            );
            setImage(condition.ordinal(), image);
        }
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        return getImage(entity.getEquipment().getCondition().ordinal());
    }
}
