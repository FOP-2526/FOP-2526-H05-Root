package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entity.Gear;
import h05.equipment.Axe;
import h05.equipment.Pickaxe;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    @DoNotTouch
    public GearDrawing() {
        super(Equipment.Condition.values().length);
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        var equipment = entity.getEquipment();
        if (equipment instanceof Axe) {
            return getImage(0);
        }
        if (equipment instanceof Pickaxe) {
            return getImage(1);
        }
        return getImage(entity.getEquipment().getCondition().ordinal());
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Gear> context) {
        Gear entity = context.entity();
        Equipment equipment = entity.getEquipment();
        Equipment.Condition[] conditions = Equipment.Condition.values();

        if (equipment instanceof Axe) {
            String path = equipment.getName() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(0, image);
        } else if (equipment instanceof Pickaxe) {
            String path = equipment.getName() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(1, image);
        }
        else {
            for (Equipment.Condition condition : conditions) {
                String path = equipment.getName() + "_" + condition.name().toLowerCase() + EXTENSION;
                Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    targetSize
                );
                setImage(condition.ordinal(), image);
            }
        }

    }
}
