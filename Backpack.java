import java.util.ArrayList;

public class Backpack {

    private ArrayList<Item> items;
    private static final int MAX_WEIGHT = 1000;

    /**
     * Construct a backpack
     */
    public Backpack(){
        this.items = new ArrayList<>();
    }

    /**
     * Add an item to the backpack
     * @param item item object to add
     */
    public boolean addItem(Item item) {
        if (item.getWeight() + getTotalWeight() > MAX_WEIGHT) {
            System.out.println("\nBackpack is too heavy. Reduce weight by dropping items.\n");
            return false;
        }
        else {
            this.items.add(item);
            System.out.println("\nGrabbed " + item.getName() + "\n");
            return true;
        }
    }

    /**
     * Remove an item of the backpack
     * @param item item object to remove
     */
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    /**
     * Get a string of what the backpack contains
     * @return a string
     */
    public String getItemsString() {
        String result = "Backpack contains:\n";

        for (Item v : this.items) {
            result += "\t- " + v.getName() + " (" + v.getWeight() + " grams)\n";
        }

        result += "\nTotal weight of backpack is: " + getTotalWeight() + "/" + MAX_WEIGHT + " grams";
        return result;

    }

    /**
     * Get the weight of the backpack
     * @return an int
     */
    public int getTotalWeight() {
        int result = 0;

        for (Item v : this.items) {
            result += v.getWeight();
        }
        return result;
    }

    /**
     * Returns an item by searching it with a string
     * @param item name of item
     * @return  item object
     */
    public Item getItemByString(String item) {
        for (Item v : this.items) {
            if (v.getName().equals(item)) {
                return v;
            }
        }
        return null;
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

}
