import java.util.ArrayList;

public class Backpack {

    private ArrayList<Item> items;
    private static final int MAX_WEIGHT = 1000;

    public Backpack(){
        this.items = new ArrayList<>();
    }

    public boolean addItem(Item item) {
        if (item.getWeight() + getTotalWeight() > MAX_WEIGHT) {
            System.out.println("Backpack is too heavy. Reduce weight by dropping items.");
            return false;
        }
        else {
            this.items.add(item);
            System.out.println("Grabbed " + item.getName());
            return true;
        }
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public String getItemsString() {
        String result = "Backpack contains:";

        for (Item v : this.items) {
            result += "\t- " + v.getName() + " (" + v.getWeight() + " grams)\n\t";
        }

        result += "Total weight of backpack is: " + getTotalWeight() +"/" + (MAX_WEIGHT - getTotalWeight()) + " grams\n";
        return result;

    }

    public int getTotalWeight() {
        int result = 0;

        for (Item v : this.items) {
            result += v.getWeight();
        }
        return result;
    }

    public Item getItemByString(String item) {
        for (Item v : this.items) {
            if (v.getName().equals(item)) {
                return v;
            }
        }
        return null;
    }

}
