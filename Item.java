public class Item {

    private String name;
    private int weight;

    /**
     * Create item and initialize it's fields
     * @param name name of item
     * @param weight weight of item in grams
     */
    public Item(String name, int weight){
        this.name = name;
        this.weight = weight;
    }

    /**
     * Return the weight of item
     * @return weight of item in grams
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Return the name of item
     * @return name of item
     */
    public String getName() {
        return this.name;
    }
}
