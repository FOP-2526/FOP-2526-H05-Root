package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.AbstractMinableEntity.AbstractMinableEntity;
import h05.Mineable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.*;

@DoNotTouch
public class NodeDrawing extends SvgBasedDrawing<AbstractMinableEntity> {

    @DoNotTouch
    public NodeDrawing() {
        super(Mineable.State.values().length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(AbstractMinableEntity entity) {
        var state = entity.getState();
        if (state.equals(Mineable.State.NOT_MINED)) {
            return getImage(0);
        }
        else if (state.equals(Mineable.State.HALF_MINED)) {
            return getImage(1);
        }
        else if (state.equals(Mineable.State.FULLY_MINED)) {
            return getImage(2);
        }
        return null;
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends AbstractMinableEntity> context) {
        var entity = context.entity();
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
