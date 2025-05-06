package h05;


import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import h05.node.Node;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class NodeDrawing implements Drawable<Node> {
    private @Nullable BufferedImage[] images = null;

    @Override
    public void draw(Graphics g, DrawingContext<Node> context) {
        Node node = context.entity();
        if (images == null) {
            var conditions = Mineable.MiningState.values();
            images = new BufferedImage[conditions.length];
            for (var state : conditions) {
                String sanitizedName = node.getName().toLowerCase().replace(" ", "");
                String path = sanitizedName + "_" + state.name().toLowerCase() + ".svg";
                InputStream in = getClass().getResourceAsStream(path);
                if (in == null) {
                    throw new IllegalArgumentException("Image not found: " + path);
                }
                images[state.ordinal()] = PaintUtils.loadFieldImage(
                        in,
                        0,
                        scale(context.colorProfile().fieldInnerSize() - context.colorProfile().fieldInnerOffset() * 2,
                                context)
                );
            }
        }
        Point upperLeft = context.upperLeftCorner();
        Image image = images[node.getMiningState().ordinal()];
        g.drawImage(image, scale(upperLeft.x, context), scale(upperLeft.y, context), null);
    }


}
