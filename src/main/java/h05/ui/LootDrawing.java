package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.entity.Loot;
import h05.loot.Mineable;
import h05.loot.MiningState;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;
import java.util.List;

/**
 * Specifies the drawing of the {@link Loot} entity.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class LootDrawing extends SvgBasedDrawing<Loot> {

    /**
     * The available mineable types that can be used by the {@link Loot} entity.
     */
    @DoNotTouch
    public static final List<Class<? extends Mineable>> AVAILABLE_MINEABLES = List.of(

    );

    /**
     * Constructs a new {@link LootDrawing} instance.
     */
    @DoNotTouch
    public LootDrawing() {
        super(AVAILABLE_MINEABLES.size() * MiningState.values().length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Loot entity) {
        Mineable mineable = entity.getMineable();
        Class<? extends Mineable> clazz = mineable.getClass();
        return getImage(
            AVAILABLE_MINEABLES.indexOf(clazz)
                * MiningState.values().length
                + mineable.getState().ordinal()
        );
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Loot> context) {
        MiningState[] states = MiningState.values();
        for (Class<? extends Mineable> clazz : AVAILABLE_MINEABLES) {
            for (MiningState state : states) {
                String path = clazz.getSimpleName() + "_" + state.name().toLowerCase() + EXTENSION;
                Image image = PaintUtils.loadFieldImage(
                    getClass().getResourceAsStream(path),
                    0,
                    targetSize
                );
                setImage(AVAILABLE_MINEABLES.indexOf(clazz) * states.length + state.ordinal(), image);
            }
        }
    }
}
