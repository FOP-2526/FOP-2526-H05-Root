package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entitity.Loot;

import java.awt.Image;

public class LootDrawing extends SvgBasedDrawing<Loot> {

    public LootDrawing() {
        super(Equipment.Condition.values().length);
    }

    @Override
    protected Image getCurrentDrawingImage(Loot entity) {
        return getImage(entity.getLoot().getCondition().ordinal());
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<Loot> context) {
        Loot entity = context.entity();
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
