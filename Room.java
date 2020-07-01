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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room upExit;
    private Room downExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room down, Room up)
    {
        if(north != null) {
            northExit = north;
        }
        if(east != null) {
            eastExit = east;
        }
        if(south != null) {
            southExit = south;
        }
        if(west != null) {
            westExit = west;
        }
        if(down != null) {
            downExit = down;
        }
        if(up != null) {
            upExit = up;
        }
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

        switch (direction.toLowerCase()) {
            case "north":
                return northExit;
            case "east":
                return eastExit;
            case "south":
                return southExit;
            case "west":
                return westExit;
            case "down":
                return downExit;
            case "up":
                return upExit;
            default:
                return null;
        }

    }

    /**
     * Return a string of all exits of the room.
     * @return a description of the existing exits in the room
     */
    public String getExitString() {
        String result = "Exits: ";
        if(northExit != null) {
            result += "north, ";
        }
        if(eastExit != null) {
            result += "east, ";
        }
        if(southExit != null) {
            result += "south, ";
        }
        if(westExit != null) {
            result += "west, ";
        }
        if(downExit != null) {
            result += "down, ";
        }
        if(upExit != null) {
            result += "up, ";
        }
        //delete comma at end
        result.substring(0,result.length()-1);

        return result;
    }

}
