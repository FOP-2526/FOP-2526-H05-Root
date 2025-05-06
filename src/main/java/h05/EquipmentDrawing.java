package h05;

import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class EquipmentDrawing implements Drawable<AbstractEquipment> {

    private @Nullable BufferedImage[] images = null;

    @Override
    public void draw(Graphics g, DrawingContext<AbstractEquipment> context) {
        AbstractEquipment entity = context.entity();
        if (images == null) {
            Equipment.Condition[] conditions = Equipment.Condition.values();
            images = new BufferedImage[conditions.length];
            for (Equipment.Condition condition : conditions) {
                String path = entity.getName() + "_" + condition.name().toLowerCase() + ".svg";
                images[condition.ordinal()] = PaintUtils.loadFieldImage(
                    getClass().getResourceAsStream(path),
                    0,
                    scale(context.colorProfile().fieldInnerSize() - context.colorProfile().fieldInnerOffset() * 2,
                        context)
                );
            }
        }
        Point upperLeft = context.upperLeftCorner();
        Image image = images[entity.getCondition().ordinal()];
        g.drawImage(image, scale(upperLeft.x, context), scale(upperLeft.y, context), null);
    }
}
