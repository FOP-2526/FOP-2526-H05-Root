package h05;

import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.World;
import org.jetbrains.annotations.Nullable;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class FogDrawing implements Drawable<Fog> {

    private @Nullable BufferedImage[] images = null;

    @Override
    public void draw(Graphics g, DrawingContext<Fog> context) {
        Fog entity = context.entity();
        if (images == null) {
            String[] types = {"ln", "dm"};
            images = new BufferedImage[types.length];
            for (int i = 0; i < types.length; i++) {
                String path = entity.getClass().getSimpleName().toLowerCase() + "_" + types[i] + ".svg";
                images[i] = PaintUtils.loadFieldImage(
                    getClass().getResourceAsStream(path),
                    0,
                    scale(context.colorProfile().fieldInnerSize() - context.colorProfile().fieldInnerOffset() * 2,
                        context)
                );
            }
        }
        Point upperLeft = context.upperLeftCorner();
        Image image = World.getGlobalWorld().getGuiPanel().isDarkMode() ? images[1] : images[0];
        g.drawImage(image, scale(upperLeft.x, context), scale(upperLeft.y, context), null);
    }
}
