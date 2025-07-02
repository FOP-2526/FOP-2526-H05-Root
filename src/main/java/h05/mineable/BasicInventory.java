package h05.mineable;

import fopbot.FieldEntity;
import fopbot.World;
import h05.base.entity.Loot;
import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

@DoNotTouch
public final class BasicInventory implements Inventory {

    @DoNotTouch
    public static final int DEFAULT_CAPACITY = 5;

    @DoNotTouch
    public static final int DEFAULT_SIZE = 5;

    @DoNotTouch
    private String[] names;

    @DoNotTouch
    private int[] amounts;

    @DoNotTouch
    private final int capacity;

    @DoNotTouch
    private int size;

    @DoNotTouch
    private int numberOfItems;

    @DoNotTouch
    private int nextIndex;

    @DoNotTouch
    public BasicInventory(int capacity, int size) {
        this.capacity = capacity;
        this.names = new String[size];
        this.amounts = new int[size];
    }

    @DoNotTouch
    public BasicInventory() {
        this(DEFAULT_CAPACITY, DEFAULT_SIZE);
    }

    @DoNotTouch
    @Override
    public int capacity() {
        return capacity;
    }

    @DoNotTouch
    @Override
    public int size() {
        return size;
    }

    @DoNotTouch
    @Override
    public int numberOfItems() {
        return numberOfItems;
    }

    @DoNotTouch
    @Override
    public boolean add(@NotNull Mineable item) {
        if (numberOfItems == capacity) {
            return false;
        }
        final String itemName = item.getName();

        // Check if item already exists in inventory
        for (int i = 0; i < size; i++) {
            if (names[i].equals(itemName)) {
                amounts[i]++;
                numberOfItems++;
                updateGui(item);
                return true;
            }
        }

        // Increase storage if necessary
        if (size == names.length) {
            final String[] newNames = new String[names.length * 2];
            final int[] newAmounts = new int[amounts.length * 2];
            System.arraycopy(names, 0, newNames, 0, names.length);
            System.arraycopy(amounts, 0, newAmounts, 0, amounts.length);
            names = newNames;
            amounts = newAmounts;
        }

        // Add new item to inventory
        names[nextIndex] = itemName;
        amounts[nextIndex] = 1;
        numberOfItems++;
        nextIndex++;
        size++;
        updateGui(item);
        return true;
    }

    @DoNotTouch
    private void updateGui(@NotNull Mineable item) {
        for (FieldEntity entity : World.getGlobalWorld().getAllFieldEntities()) {
            if (entity instanceof Loot loot && loot.getMineable().equals(item)) {
                World.getGlobalWorld().removeEntity(entity);
            }
        }
    }

    @DoNotTouch
    @Override
    public String[] getNames() {
        return names;
    }

    @DoNotTouch
    @Override
    public int getAmount(@NotNull String itemName) {
        for (int i = 0; i < size; i++) {
            if (names[i].equals(itemName)) {
                return amounts[i];
            }
        }
        return 0;
    }
}
