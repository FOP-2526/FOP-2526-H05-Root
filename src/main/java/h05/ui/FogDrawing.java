package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import fopbot.World;
import h05.entity.Fog;
import h05.game.GameConstants;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Graphics;
import java.awt.Image;

@DoNotTouch
public class FogDrawing extends SvgBasedDrawing<Fog> {

    @DoNotTouch
    private static final String[] TYPES = {"ln", "dm"};

    @DoNotTouch
    public FogDrawing() {
        super(TYPES.length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Fog entity) {
        return World.getGlobalWorld().getGuiPanel().isDarkMode() ? getImage(1) : getImage(0);
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Fog> context) {
        Fog entity = context.entity();
        String suffix = entity.getClass().getSimpleName().toLowerCase();
        for (int i = 0; i < TYPES.length; i++) {
            String type = TYPES[i];
            String path = suffix + "_" + type + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                getClass().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(i, image);
        }
    }

    @Override
    public void draw(Graphics g, DrawingContext<? extends Fog> context) {
        if (!GameConstants.FOG_VISIBILITY) {
            return;
        }
        super.draw(g, context);
    }
}


