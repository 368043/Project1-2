import java.util.ArrayList;

public class Workbench {
    private Item item;
    private ArrayList<String> components;

    public Workbench(ArrayList components, Item item){
        this.item = item;
        this.components = components;
    }

    public String stringDescription(){
        String result = "Here you can build " + this.item.getName() + " with the following items:\n";

        for (String v : this.components){
            result += "\t- "+v+"\n";
        }

        return result;
    }

    public String getItemString(){
        return this.item.getName();
    }

    public ArrayList<String> returnComponents(){
        return this.components;
    }

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
