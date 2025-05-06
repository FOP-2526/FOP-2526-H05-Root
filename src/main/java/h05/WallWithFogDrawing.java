package h05;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.Wall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class WallWithFogDrawing implements Drawable<Wall> {

    @Override
    public void draw(Graphics g, DrawingContext<Wall> context) {
        if (context.field().contains(Fog.class)) {
            return;
        }
        final Color oldColor = g.getColor();
        final ColorProfile colorProfile = context.colorProfile();
        final Point upperLeft = context.upperLeftCorner();

        g.setColor(colorProfile.getWallColor());
        if (context.entity().isHorizontal()) {
            final int x = upperLeft.x - colorProfile.fieldInnerOffset() * 2;
            final int y = upperLeft.y - colorProfile.fieldInnerOffset() - colorProfile.fieldBorderThickness();
            g.fillRect(
                scale(x, context),
                scale(y, context),
                scale(colorProfile.fieldInnerSize() + colorProfile.fieldInnerOffset() * 2, context),
                scale(colorProfile.fieldBorderThickness(), context)
            );
        } else {
            final int x = upperLeft.x - colorProfile.fieldInnerOffset() + colorProfile.fieldInnerSize();
            final int y = upperLeft.y - colorProfile.fieldInnerOffset() * 2;
            g.fillRect(
                scale(x, context),
                scale(y, context),
                scale(colorProfile.fieldBorderThickness(), context),
                scale(colorProfile.fieldInnerSize() + colorProfile.fieldInnerOffset() * 2, context)
            );
        }

        g.setColor(oldColor);

    }
}
