package h05.base.ui;

import fopbot.DrawingContext;
import fopbot.PaintUtils;
import fopbot.SvgBasedDrawing;
import h05.base.entity.Gear;
import h05.equipment.Axe;
import h05.equipment.Battery;
import h05.equipment.Camera;
import h05.equipment.Equipment;
import h05.equipment.EquipmentCondition;
import h05.equipment.Pickaxe;
import h05.equipment.Powerbank;
import h05.equipment.TelephotoLens;
import h05.equipment.Tool;
import h05.equipment.WallBreaker;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Image;
import java.util.List;

@DoNotTouch
public class GearDrawing extends SvgBasedDrawing<Gear> {

    /**
     * The available equipment types that can be used by the {@link Gear} entity.
     */
    @DoNotTouch
    public static final List<Class<? extends Equipment>> AVAILABLE_EQUIPMENTS = List.of(
        Battery.class,
        Camera.class,
        Powerbank.class,
        TelephotoLens.class,
        WallBreaker.class
    );

    /**
     * The available tool types that can be used by the {@link Gear} entity.
     */
    @DoNotTouch
    public static final List<Class<? extends Tool>> AVAILABLE_TOOLS = List.of(
        Axe.class,
        Pickaxe.class
    );

    /**
     * Constructs a new {@link GearDrawing} instance.
     */
    @DoNotTouch
    public GearDrawing() {
        super(AVAILABLE_EQUIPMENTS.size() * EquipmentCondition.values().length + AVAILABLE_TOOLS.size());
    }

    @DoNotTouch
    @Override
    protected Image getCurrentDrawingImage(Gear entity) {
        final Equipment equipment = entity.getEquipment();
        final int index;
        final Class<? extends Equipment> clazz = equipment.getClass();
        final int numberOfConditions = EquipmentCondition.values().length;
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
        final EquipmentCondition[] conditions = EquipmentCondition.values();
        for (Class<? extends Equipment> clazz : AVAILABLE_EQUIPMENTS) {
            for (EquipmentCondition condition : conditions) {
                final String path = clazz.getSimpleName().toLowerCase() + "_" + condition.name().toLowerCase() + EXTENSION;
                final Image image = PaintUtils.loadFieldImage(
                    getClass().getResourceAsStream(path),
                    0,
                    targetSize
                );
                setImage(AVAILABLE_EQUIPMENTS.indexOf(clazz) * conditions.length + condition.ordinal(), image);
            }
        }
        final int offset = AVAILABLE_EQUIPMENTS.size() * conditions.length;
        for (Class<? extends Tool> clazz : AVAILABLE_TOOLS) {
            final String path = clazz.getSimpleName().toLowerCase() + EXTENSION;
            final Image image = PaintUtils.loadFieldImage(
                getClass().getResourceAsStream(path),
                0,
                targetSize
            );
            setImage(offset + AVAILABLE_TOOLS.indexOf(clazz), image);
        }
    }
}
