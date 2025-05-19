package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entity.Gear;
import h05.gear.Axe;
import h05.gear.Battery;
import h05.gear.Camera;
import h05.gear.Pickaxe;
import h05.gear.Tool;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;
import java.util.List;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    // TODO: Add your own equipment classes to the list
    @DoNotTouch
    public static final List<Class<? extends Equipment>> AVAILABLE_EQUIPMENTS = List.of(
        Battery.class,
        Camera.class
        //Powerbank.class,
        //TelephotoLens.class
    );

    @DoNotTouch
    public static final List<Class<? extends Tool>> AVAILABLE_TOOLS = List.of(
        Axe.class,
        Pickaxe.class
    );

    @DoNotTouch
    public GearDrawing() {
        super(AVAILABLE_EQUIPMENTS.size() * Equipment.Condition.values().length + AVAILABLE_TOOLS.size());
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        var equipment = entity.getEquipment();
        int index;
        Class<? extends Equipment> clazz = equipment.getClass();
        int numberOfConditions = Equipment.Condition.values().length;
        if (equipment instanceof Tool) {
            index = AVAILABLE_EQUIPMENTS.size() * numberOfConditions + AVAILABLE_TOOLS.indexOf(clazz);
        } else {
            index = AVAILABLE_EQUIPMENTS.indexOf(clazz) * numberOfConditions + equipment.getCondition().ordinal();
        }
        return getImage(index);
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Gear> context) {
        Equipment.Condition[] conditions = Equipment.Condition.values();
        for (Class<? extends Equipment> clazz : AVAILABLE_EQUIPMENTS) {
            for (Equipment.Condition condition : conditions) {
                String path = clazz.getSimpleName() + "_" + condition.name().toLowerCase() + EXTENSION;
                Image image = PaintUtils.loadFieldImage(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                    0,
                    targetSize
                );
                setImage(AVAILABLE_EQUIPMENTS.indexOf(clazz) * conditions.length + condition.ordinal(), image);
            }
        }
        int offset = AVAILABLE_EQUIPMENTS.size() * conditions.length;
        for (Class<? extends Tool> clazz : AVAILABLE_TOOLS) {
            String path = clazz.getSimpleName() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(offset + AVAILABLE_TOOLS.indexOf(clazz), image);
        }
    }
}
