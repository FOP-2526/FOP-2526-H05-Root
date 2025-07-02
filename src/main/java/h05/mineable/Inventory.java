package h05.mineable;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public interface Inventory {

    @DoNotTouch
    int capacity();

    @DoNotTouch
    int size();

    @DoNotTouch
    int numberOfItems();

    @DoNotTouch
    boolean add(@NotNull Mineable item);

    @DoNotTouch
    @NotNull
    String[] getNames();

    @DoNotTouch
    int getAmount(@NotNull String itemName);
}
