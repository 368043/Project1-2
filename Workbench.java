import java.util.ArrayList;

public class Workbench {
    private Item item;
    private String[] components;

    public Workbench(String[] components, Item item){
        this.item = item;
        this.components = components;
    }

    public void printDescription(){
        System.out.println("Here you can build " + this.item.getName() + " with the following items:\n");
        for (String v : this.components){
            System.out.println("\t- v\n");
        }
    }

    public Item buildItem(String[] items){
        int amount = this.components.length;

        int check = 0;

        for (String v: items){
            for (String v2: this.components){
                if (v.equals(v2)){
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
