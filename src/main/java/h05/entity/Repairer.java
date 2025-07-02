package h05.entity;

import h05.base.game.GameSettings;
import h05.base.game.TickBased;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.Point;

@DoNotTouch
public interface Repairer extends TickBased {

    @DoNotTouch
    @NotNull
    GameSettings getGameSettings();

    @DoNotTouch
    int getRadius();

    @DoNotTouch
    @Nullable
    Point scan();

    @DoNotTouch
    void repair(@NotNull Point point);
}
