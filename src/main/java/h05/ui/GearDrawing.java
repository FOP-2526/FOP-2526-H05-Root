package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entitity.Gear;

import java.awt.Image;

public class GearDrawing extends SvgBasedDrawing<Gear> {

    public GearDrawing() {
        super(Equipment.Condition.values().length);
    }

    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        return getImage(entity.getLoot().getCondition().ordinal());
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<Gear> context) {
        Gear entity = context.entity();
        Equipment equipment = entity.getLoot();
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
