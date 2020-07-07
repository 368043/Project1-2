import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction The direction of exit.
     * @param neighbor The room in that direction.
     */
    public void setExits(String direction, Room neighbor)
    {
        this.exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Get room info of the direction
     *
     * @param direction direction of room (north, east, south, west, up, down)
     * @return Room of direction
     */
    public Room getExit(String direction) {
        return this.exits.get(direction);
    }

    /**
     * Return a string of all exits of the room.
     * @return a description of the existing exits in the room
     */
    public String getExitString() {
        String result = "Exits: ";

        Set<String> keys = this.exits.keySet();

        for (String v : keys) {
            result += v + ", ";
        }

        //delete comma and space at end
        return result.substring(0,result.length()-2);
    }

    /**
     * Return an extensive description of the room
     * @return a description of the room
     */
    public String getLongDescription(){
        return "You are " + this.description + ".\n" + getExitString() + "\n" + getItemsString();
    }

    /**
     * Add object to room
     * @param itemName name of items
     * @param weight weight of items in grams
     */
    public void setItems(String itemName, int weight){
        this.items.add(new Item(itemName, weight));
    }

    /**
     * Return a string of all items in the room
     * @return A string of all items in the room
     */
    public String getItemsString() {
        String result = "Items: ";

        for (Item v : items) {
            result += "\t- " + v.getName() + " (" + v.getWeight() + " grams)\n";
        }

        return result;
    }

    public Item getItem(String itemName) {
        for (Item v : this.items) {
            if (v.getName().equals(itemName)) {
                return v;
            }
        }
        return null;
    }

    public boolean removeItem(String itemName) {
        for (Item v : this.items) {
            if (v.getName().equals(itemName)) {
                return items.remove(v);
            }
        }
        return false;
    }

}
