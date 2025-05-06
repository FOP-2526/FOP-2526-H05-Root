package h05.iu;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.PaintUtils;
import h05.Equipment;
import h05.EquippedBot;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class EquippedBotDrawing implements Drawable<EquippedBot> {

    private static final double EQUIPMENT_SIZE_SCALE = 20d;
    private static final double BATTERY_PADDING_SCALE = 7d;

    @Override
    public void draw(Graphics g, DrawingContext<EquippedBot> context) {
        final EquippedBot robot = context.entity();

        // Skip drawing if the robot is turned off and we don't want to draw turned-off robots
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

        // Draw the robot
        final Image robotImage = robot.getRobotFamily().render(
            robotSize,
            rotationDegrees,
            robot.isTurnedOff()
        );

        g.drawImage(robotImage, scaledX, scaledY, null);


        // Load and draw camera and battery images
        Graphics2D g2d = (Graphics2D) g;
        final double equipmentSize = scale(20d, context);
        if (robot.getCamera() != null) {
            final BufferedImage cameraImage = loadEquipmentImage(robot.getCamera(), context, rotationDegrees);
            double x, y;
            y = switch (robot.getDirection()) {
                case UP -> {
                    x = scaledX;
                    yield scaledY;
                }
                case RIGHT -> {
                    x = scaledX + robotSize - equipmentSize;
                    yield scaledY;
                }
                case DOWN -> {
                    x = scaledX + robotSize - equipmentSize;
                    yield scaledY + robotSize - equipmentSize;
                }
                case LEFT -> {
                    x = scaledX;
                    yield scaledY + robotSize - equipmentSize;
                }
            };
            g2d.drawImage(cameraImage, (int) x, (int) y, (int) equipmentSize, (int) equipmentSize, null);
        }
        if (robot.getBattery() != null) {
            final BufferedImage batteryImage = loadEquipmentImage(robot.getBattery(), context, 90 * (robot.getDirection().ordinal() + 1));
            final double padding = scale(3, context);
            double x = 0, y = 0;
            y = switch (robot.getDirection()) {
                case UP, RIGHT -> {
                    x = scaledX - padding;
                    yield scaledY + robotSize - equipmentSize;
                }
                case DOWN, LEFT -> {
                    x = scaledX + robotSize - equipmentSize - padding;
                    yield scaledY;
                }
            };

            g2d.drawImage(batteryImage, (int) x, (int) y, (int) equipmentSize, (int) equipmentSize, null);
        }
    }

    private BufferedImage loadEquipmentImage(Equipment equipment, DrawingContext<EquippedBot> context, int rotationDegrees) {
        String path = equipment.getName() + "_" + equipment.getCondition().name().toLowerCase() + ".svg";
        return PaintUtils.loadFieldImage(getClass().getResourceAsStream(path), rotationDegrees, (int) scale(EQUIPMENT_SIZE_SCALE, context));
    }
}
