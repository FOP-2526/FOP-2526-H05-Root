package h05.ui;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import h05.entity.MiningRobot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class MineBotDrawing implements Drawable<MiningRobot> {

    @Override
    public void draw(Graphics g, DrawingContext<? extends MiningRobot> context) {
        final MiningRobot robot = context.entity();

        if (robot.isTurnedOff() && !context.world().isDrawTurnedOffRobots()) {
            return;
        }

        final ColorProfile profile = context.colorProfile();
        final Point upperLeft = context.upperLeftCorner();

        final int rotationDegrees = robot.getDirection().ordinal() * 90;
        final int robotSize = scale(profile.fieldInnerSize() - profile.fieldInnerOffset() * 2, context);

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
    }
}
