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
}
