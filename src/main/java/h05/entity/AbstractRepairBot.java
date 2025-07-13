package h05.entity;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import h05.base.game.GameSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.Student;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.awt.*;

@DoNotTouch
public abstract class AbstractRepairBot extends Robot implements Repairer {

    private final @NotNull GameSettings settings;

    private final int radius;

    public AbstractRepairBot(int x, int y, @NotNull GameSettings settings, int radius) {
        super(x, y, Direction.UP, 0, RobotFamily.SQUARE_RED);
        this.settings = settings;
        this.radius = radius;
    }

    @Override
    public @NotNull GameSettings getGameSettings() {
        return settings;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @StudentImplementationRequired("H5.5")
    @Override
    public @Nullable Point scan() {
        return Student.crash();
    }

    @StudentImplementationRequired("H5.5")
    @Override
    public void repair(@NotNull Point point) {
        Student.crash();
    }

    protected abstract void move(@NotNull Point point);
}
