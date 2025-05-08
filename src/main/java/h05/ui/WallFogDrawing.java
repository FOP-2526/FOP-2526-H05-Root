package h05.ui;

import fopbot.ColorProfile;
import fopbot.Drawable;
import fopbot.DrawingContext;
import fopbot.KarelWorld;
import fopbot.Wall;
import h05.Fog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class WallFogDrawing implements Drawable<Wall> {

    @Override
    public void draw(Graphics g, DrawingContext<Wall> context) {
        final Wall entity = context.entity();
        final KarelWorld world = context.world();
        final ColorProfile profile = context.colorProfile();
        final Point upperLeft = context.upperLeftCorner();

        final int x = entity.getX();
        final int y = entity.getY();
        final boolean isHorizontal = entity.isHorizontal();
        final boolean isFogHere = context.field().contains(Fog.class);
        final boolean isFogInNextField = isHorizontal
            ? (y + 1 < world.getHeight() && world.getField(x, y + 1).contains(Fog.class))
            : (x + 1 < world.getWidth() && world.getField(x + 1, y).contains(Fog.class));

        if (isFogHere && isFogInNextField) {
            return;
        }

        final Color oldColor = g.getColor();
        g.setColor(profile.getWallColor());

        final int offset = profile.fieldInnerOffset();
        final int thickness = profile.fieldBorderThickness();
        final int innerSize = profile.fieldInnerSize();
        int drawX;
        int drawY;
        int width;
        int height;
        if (isHorizontal) {
            drawX = upperLeft.x - offset * 2;
            drawY = upperLeft.y - offset - thickness;
            width = innerSize + offset * 2;
            height = thickness;
        } else {
            drawX = upperLeft.x - offset + innerSize;
            drawY = upperLeft.y - offset * 2;
            width = thickness;
            height = innerSize + offset * 2;
        }
        g.fillRect(
            scale(drawX, context),
            scale(drawY, context),
            scale(width, context),
            scale(height, context)
        );

        g.setColor(oldColor);
    }
}
