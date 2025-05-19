package h05.ui;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.FieldEntity;
import fopbot.PaintUtils;
import h05.Equipment;
import h05.entity.MiningRobot;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

@DoNotTouch
public class MineBotDrawing implements Drawable<MiningRobot> {

    @DoNotTouch
    private static final double EQUIPMENT_SIZE_SCALE = 15d;

    @DoNotTouch
    @Override
    public void draw(Graphics g, DrawingContext<? extends MiningRobot> context) {
        final MiningRobot robot = context.entity();

        if (robot.isTurnedOff() && !context.world().isDrawTurnedOffRobots()) {
            return;
        }

        final ColorProfile profile = context.colorProfile();
        final Point upperLeft = context.upperLeftCorner();
        final int offset = profile.fieldInnerOffset();
        final int robotSize = scale(profile.fieldInnerSize() - offset * 2, context);
        final int scaledX = scale(upperLeft.x, context);
        final int scaledY = scale(upperLeft.y, context);
        final int rotationDegrees = robot.getDirection().ordinal() * 90;
        final Image robotImage = robot.getRobotFamily().render(
            robotSize,
            rotationDegrees,
            robot.isTurnedOff() || robot.isBatteryBroken()
        );

        g.drawImage(
            robotImage,
            scale(upperLeft.x, context),
            scale(upperLeft.y, context),
            null
        );

        // Draw overlay battery
        Graphics2D g2d = (Graphics2D) g;
        final double equipmentSize = scale(20d, context);
        double x;
        double y;
        y = switch (robot.getDirection()) {
            case UP, RIGHT -> {
                x = scaledX;
                yield scaledY + robotSize - equipmentSize;
            }
            case DOWN, LEFT -> {
                x = scaledX + robotSize - equipmentSize;
                yield scaledY;
            }
        };
        final Image batteryImage = loadEquipmentImage(robot.getBattery(), context, rotationDegrees - 90);
        g2d.drawImage(batteryImage, (int) x, (int) y, (int) equipmentSize, (int) equipmentSize, null);
    }

    @DoNotTouch
    private Image loadEquipmentImage(Equipment equipment, DrawingContext<? extends FieldEntity> context, int rotationDegrees) {
        String path = equipment.getName() + "_" + equipment.getCondition().name().toLowerCase() + ".svg";
        return PaintUtils.loadFieldImage(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), rotationDegrees, (int) scale(EQUIPMENT_SIZE_SCALE, context));
    }
}
