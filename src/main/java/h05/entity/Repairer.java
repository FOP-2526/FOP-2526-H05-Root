package h05.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

@DoNotTouch
public interface Repairer {

    @DoNotTouch
    int getRadius();

    @DoNotTouch
    @Nullable Point scan();

    @DoNotTouch
    void repair(@NotNull Point point);
}
