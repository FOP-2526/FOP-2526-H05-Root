package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Mineable;
import h05.entity.Loot;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.*;

@DoNotTouch
public class LootDrawing extends SvgBasedDrawing<Loot> {

    @DoNotTouch
    public LootDrawing() {
        super(Mineable.State.values().length);
    }

    @Override
    protected void loadImages(int i, DrawingContext<Loot> drawingContext) {
        Loot entity = drawingContext.entity();
        Mineable mineable = entity.getMineable();
        Mineable.State[] states = Mineable.State.values();
        for (Mineable.State state : states) {
            String path = mineable.getName() + "_" + state.name().toLowerCase() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    i);
            setImage(state.ordinal(), image);
        }
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Loot entity) {
        return getImage(entity.getMineable().getState().ordinal());
    }
}
