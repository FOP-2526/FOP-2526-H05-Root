package h05.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.Equipment;
import h05.entity.Gear;
import h05.equipment.Axe;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Pickaxe;
import h05.equipment.Powerbank;
import h05.equipment.TelephotoLens;
import h05.equipment.Tool;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;
import java.util.List;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    public static final List<Class<? extends Equipment>> AVAILABLE_EQUIPMENTS = List.of(
        Battery.class,
        Camera.class
        //Powerbank.class,
        //TelephotoLens.class
    );

    public static final List<Class<? extends Tool>> AVAILABLE_TOOLS = List.of(
        Axe.class,
        Pickaxe.class
    );

    public static final int EQUIPMENTS_IMAGES = 4;

    public static final int TOOL_IMAGES = 1;

    @DoNotTouch
    public GearDrawing() {
        super(
            AVAILABLE_EQUIPMENTS.size() * EQUIPMENTS_IMAGES
                + AVAILABLE_TOOLS.size() * TOOL_IMAGES
        );
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        var equipment = entity.getEquipment();
        int index;
        Class<? extends Equipment> clazz = equipment.getClass();
        if (equipment instanceof Tool) {
            index = AVAILABLE_EQUIPMENTS.size() * EQUIPMENTS_IMAGES + AVAILABLE_TOOLS.indexOf(clazz) * TOOL_IMAGES;
        } else {
            index = AVAILABLE_EQUIPMENTS.indexOf(clazz) * EQUIPMENTS_IMAGES + equipment.getCondition().ordinal();
        }
        return getImage(index);
    }

    @DoNotTouch
    @Override
    protected void loadImages(int targetSize, DrawingContext<? extends Gear> context) {
        Gear entity = context.entity();
        Equipment.Condition[] conditions = Equipment.Condition.values();
        for (Class<? extends Equipment> clazz : AVAILABLE_EQUIPMENTS) {
            for (Equipment.Condition condition : conditions) {
                String path = clazz.getSimpleName() + "_" + condition.name().toLowerCase() + EXTENSION;
                try {
                    Image image = PaintUtils.loadFieldImage(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                        0,
                        targetSize
                    );
                    setImage(AVAILABLE_EQUIPMENTS.indexOf(clazz) * EQUIPMENTS_IMAGES + condition.ordinal(), image);
                } catch (Exception e) {
                    System.err.println(path);
                    throw e;
                }

            }
        }
        int offset = AVAILABLE_EQUIPMENTS.size() * EQUIPMENTS_IMAGES;
        for (Class<? extends Tool> clazz : AVAILABLE_TOOLS) {
            String path = clazz.getSimpleName() + EXTENSION;
            Image image = PaintUtils.loadFieldImage(
                Thread.currentThread().getContextClassLoader().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(offset + AVAILABLE_TOOLS.indexOf(clazz) * TOOL_IMAGES, image);
        }
    }
}
