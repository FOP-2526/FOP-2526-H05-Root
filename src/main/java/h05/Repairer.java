package h05;

import org.jetbrains.annotations.Nullable;

import java.awt.Point;

public interface Repairer {

    int getRadius();

    @Nullable Point scan();

    void repair(Point point);
}
