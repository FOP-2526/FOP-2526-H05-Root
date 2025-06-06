package h05.loot;

/**
 * A basic implementation of an inventory that can hold mineable items.
 *
 * @author Nhan Huynh, Nico Schnieders
 * @see Inventory
 */
public class BasicInventory implements Inventory {

    /**
     * The default capacity of the inventory.
     */
    public static final int DEFAULT_CAPACITY = 5;

    /**
     * The default size of the inventory.
     */
    public static final int DEFAULT_SIZE = 5;

    /**
     * The names of the items in the inventory.
     */
    private String[] names;

    /**
     * The amounts of each item in the inventory.
     */
    private int[] amounts;

    /**
     * The maximum number of items the inventory can hold.
     */
    private final int capacity;

    /**
     * The current size of the inventory, i.e., the number of different items it can hold.
     */
    private int size;

    /**
     * The number of items currently in the inventory.
     */
    private int numberOfItems;

    /**
     * The index for the next item to be added to the inventory.
     */
    private int nextIndex;

    /**
     * Constructs a new {@link BasicInventory} instance with the specified capacity and size.
     *
     * @param capacity the maximum number of items the inventory can hold
     * @param size     the initial size of the inventory
     */
    public BasicInventory(int capacity, int size) {
        this.capacity = capacity;
        this.names = new String[size];
        this.amounts = new int[size];
    }

    /**
     * Constructs a new {@link BasicInventory} instance with the capacity of {@value #DEFAULT_CAPACITY} and size
     * of {@value #DEFAULT_SIZE}.
     */
    public BasicInventory() {
        this(DEFAULT_CAPACITY, DEFAULT_SIZE);
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int numberOfItems() {
        return numberOfItems;
    }

    @Override
    public boolean add(Mineable item) {
        if (numberOfItems == capacity) {
            return false;
        }
        String itemName = item.getName();
        for (int i = 0; i < size; i++) {
            if (names[i].equals(itemName)) {
                amounts[i]++;
                numberOfItems++;
                return true;
            }
        }

        if (size == names.length) {
            String[] newNames = new String[names.length * 2];
            int[] newAmounts = new int[amounts.length * 2];
            System.arraycopy(names, 0, newNames, 0, names.length);
            System.arraycopy(amounts, 0, newAmounts, 0, amounts.length);
            names = newNames;
            amounts = newAmounts;
        }

        names[nextIndex] = itemName;
        amounts[nextIndex] = 1;
        numberOfItems++;
        nextIndex++;
        size++;
        return true;
    }


    @Override
    public String[] getNames() {
        return names;
    }

    @Override
    public int getAmount(String itemName) {
        for (int i = 0; i < size; i++) {
            if (names[i].equals(itemName)) {
                return amounts[i];
            }
        }
        return 0;
    }
}
