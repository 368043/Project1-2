import java.util.ArrayList;

public class Backpack {

    private ArrayList<Item> items;

    public Backpack(){
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        this.items.add(item);
        System.out.println("Grabbed " + item.getName());
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public String getItemsString() {
        String result = "Backpack contains:";

        for (Item v : this.items) {
            result += "\t- " + v.getName() + " (" + v.getWeight() + " grams)\n";
        }

        return result;

    }

    public int getTotalWeight() {
        int result = 0;

        for (Item v : this.items) {
            result += v.getWeight();
        }
        return result;
    }

}
