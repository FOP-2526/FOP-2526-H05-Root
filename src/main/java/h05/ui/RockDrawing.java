package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Mineable;
import h05.node.Node;
import h05.node.Rock;
import h05.node.Tree;

import java.awt.*;

public class RockDrawing extends SvgBasedDrawing<Rock> {

    public RockDrawing() {
        super(Mineable.State.values().length);
    }
    protected Image getCurrentDrawingImage(Rock entity) {
        return getImage(0);
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<Rock> context) {
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
