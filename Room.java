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
    private ArrayList<String> objects;

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
        this.objects = new ArrayList<>();
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

    public String getLongDescription(){
        return "You are " + this.description + ".\n" + getExitString();
    }

    public void setObjects(String object){
        this.objects.add(object);
    }

    public boolean containsObject(String object){

        for (String v : this.objects) {
            if (v.equals(object)) {
                return true;
            }
        }

        return false;
    }

}
