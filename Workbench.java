import java.util.ArrayList;

public class Workbench {
    private Item item;
    private ArrayList<String> components;

    /**
     * Constructs a new workbench
     * @param components Arraylist of needed components
     * @param item Item that is buildable
     */
    public Workbench(ArrayList components, Item item){
        this.item = item;
        this.components = components;
    }

    /**
     * Returns a String of the description of the workbench
     * @return String of description
     */
    public String stringDescription(){
        String result = "Here you can build '" + this.item.getName() + "'\nwith the following items:";

        for (String v : this.components){
            result += "\n\t- "+v;
        }

        return result;
    }

    /**
     * Returns a String of the name of item
     * @return String of name of item
     */
    public String getItemString(){
        return this.item.getName();
    }

    /**
     * Returns Arraylist of needed components
     * @return Arraylist with needed components
     */
    public ArrayList<String> returnComponents(){
        return this.components;
    }

    /**
     * Try to build items with given Arraylist with items
     * @param items Arraylist with items
     * @return builded item
     */
    public Item buildItem(ArrayList<Item> items){
        int amount = this.components.size();

        int check = 0;

        for (Item v: items){
            for (String v2: this.components){
                if (v.getName().equals(v2)){
                    check++;
                }
            }
        }

        if (check==amount){
            return this.item;
        }
        else {
            return null;
        }
    }


}
