package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import fopbot.World;
import h05.Fog;

import java.awt.Image;

public class FogDrawing extends SvgBasedDrawing<Fog> {

    private static final String[] TYPES = {"ln", "dm"};

    public FogDrawing() {
        super(TYPES.length);
    }

    @Override
    protected Image getCurrentDrawingImage(Fog entity) {
        return World.getGlobalWorld().getGuiPanel().isDarkMode() ? getImage(1) : getImage(0);
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<Fog> context) {
        Fog entity = context.entity();
        String suffix = entity.getClass().getSimpleName().toLowerCase();
        for (int i = 0; i < TYPES.length; i++) {
            String type = TYPES[i];
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


