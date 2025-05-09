package h05.ui;


import fopbot.*;
import h05.Mineable;
import h05.node.Node;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class NodeDrawing extends SvgBasedDrawing<Node> {

    public NodeDrawing() {
        super(Mineable.State.values().length);
    }
    protected Image getCurrentDrawingImage(Node entity) {
        return getImage(0);
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<Node> context) {
        Node entity = context.entity();
        String suffix = entity.getClass().getSimpleName().toLowerCase();
        for (int i = 0; i < Mineable.State.values().length; i++) {
            String type = Mineable.State.values()[i].name().toLowerCase();
            String path = suffix + "_" + type + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    targetSize
            );
            setImage(i, image);
        }
    }

//    @Override
//    public void draw(Graphics g, DrawingContext<Node> context) {
//        Node node = context.entity();
//        if (images == null) {
//            var conditions = Mineable.State.values();
//            images = new BufferedImage[conditions.length];
//            for (var state : conditions) {
//                String sanitizedName = node.getName().toLowerCase().replace(" ", "");
//                String path = sanitizedName + "_" + state.name().toLowerCase() + ".svg";
//                InputStream in = getClass().getResourceAsStream(path);
//                if (in == null) {
//                    throw new IllegalArgumentException("Image not found: " + path);
//                }
//                images[state.ordinal()] = PaintUtils.loadFieldImage(
//                        in,
//                        0,
//                        scale(context.colorProfile().fieldInnerSize() - context.colorProfile().fieldInnerOffset() * 2,
//                                context)
//                );
//            }
//        }
//        Point upperLeft = context.upperLeftCorner();
//        Image image = images[node.getMiningState().ordinal()];
//        g.drawImage(image, scale(upperLeft.x, context), scale(upperLeft.y, context), null);
//    }


}
