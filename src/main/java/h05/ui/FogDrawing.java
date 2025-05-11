package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import fopbot.World;
import h05.entity.Fog;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.*;

@DoNotTouch
public class FogDrawing extends SvgBasedDrawing<Fog> {

    @DoNotTouch
    private static final String[] TYPES = {"ln", "dm"};

    @DoNotTouch
    public FogDrawing() {
        super(TYPES.length);
    }

    @Override
    protected void loadImages(int j, DrawingContext<Fog> drawingContext) {
        Fog entity = drawingContext.entity();
        String suffix = entity.getClass().getSimpleName().toLowerCase();
        for (int i = 0; i < TYPES.length; i++) {
            String type = TYPES[i];
            String path = suffix + "_" + type + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    j
            );
            setImage(i, image);
        }
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Fog entity) {
        return World.getGlobalWorld().getGuiPanel().isDarkMode() ? getImage(1) : getImage(0);
    }
}


