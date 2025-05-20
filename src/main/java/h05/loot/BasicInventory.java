package h05.loot;

public class BasicInventory implements Inventory {

    public static final int DEFAULT_CAPACITY = 5;

    public static final int DEFAULT_SIZE = 5;

    private String[] names;
    private int[] amounts;
    private final int capacity;
    private int size;
    private int numberOfItems;
    private int nextIndex;

    public BasicInventory(int capacity, int size) {
        this.capacity = capacity;
        this.names = new String[size];
        this.amounts = new int[size];
    }

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
