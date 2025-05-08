package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.Mineable;
import h05.entitity.Loot;

import java.awt.Image;

public class LootDrawing extends SvgBasedDrawing<Loot> {

    public LootDrawing() {
        super(Mineable.State.values().length);
    }

    @Override
    protected Image getCurrentDrawingImage(Loot entity) {
        return getImage(entity.getMineable().getState().ordinal());
    }

    @Override
    protected void loadImages(int targetSize, DrawingContext<Loot> context) {
        Loot entity = context.entity();
        Mineable mineable = entity.getMineable();
        Mineable.State[] states = Mineable.State.values();
        for (Mineable.State state : states) {
            String path = mineable.getName() + "_" + state.name().toLowerCase() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    targetSize
            );
            setImage(state.ordinal(), image);
        }
    }
}
