import java.util.Stack;

public class Player {

    private Backpack backpack;
    private Room currentRoom;
    private Stack<Room> roomHistory;

    /**
     * Constructs a new player with a starting room
     * @param startRoom the room object where the player starts
     */
    public Player(Room startRoom){
        this.backpack = new Backpack();
        this.currentRoom = startRoom;
        roomHistory = new Stack<>();
    }

    /**
     * returns the room where the player currently at
     * @return a room object
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Changes the current room to a new room
     * @param newRoom the new room object
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    /**
     * Try to go to a new room in a particular direction
     * @param direction string of direction (north, east, south, west)
     * @return true or false
     */
    public boolean goRoom(String direction) {
        Room newRoom = this.currentRoom.getExit(direction);

        if (newRoom instanceof LockedRoom) {
            if (((LockedRoom) newRoom).isLocked()) {
                return false;
            }
            else {
                this.roomHistory.push(this.currentRoom);
                this.currentRoom = newRoom;
                return true;
            }
        }
        else if (newRoom == null) {
            return false;
        }
        else {
            this.roomHistory.push(this.currentRoom);
            this.currentRoom = newRoom;
            return true;
        }
    }

    /**
     * Returns string of the items that the player haves.
     * @return a String
     */
    public String getItemsString() {
        return this.backpack.getItemsString();
    }

    /**
     * Try to grab an item by giving the name of the item
     * @param itemName String of the name of the item
     * @return true or false
     */
    public boolean grabItem(String itemName) {
        Item item = this.currentRoom.getItem(itemName);

        if (item != null) {
            if (this.backpack.addItem(item)){
                this.currentRoom.removeItem(itemName);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * try to drop an item by giving the name of the item
     * @param item String of the name of the item
     * @return true or false
     */
    public boolean dropItem(String item) {
        Item temp = this.backpack.getItemByString(item);

        if (temp != null) {
            this.backpack.removeItem(temp);
            this.currentRoom.setItems(temp);
            return true;
        }
        else {
            return false;
        }

    }

    /**
     * Try to go to previous visited room
     * @return true or false
     */
    public boolean previousRoom() {
        if (roomHistory.empty()) {
            return false;
        }
        else {
            setCurrentRoom(roomHistory.pop());
            return true;
        }
    }

    /**
     * Try to open an room of a particular direction with an item
     * @param item String of the name of the item
     * @param direction String of the direction
     * @return true or false
     */
    public boolean openRoom(String item, String direction) {
        Room target = this.currentRoom.getExit(direction);
        Item tempItem = this.backpack.getItemByString(item);

        if (target == null) {
            return false;
        }
        else if (tempItem==null){
            System.out.println("You don't have this item.");
            return false;
        }
        else {
            if (target instanceof LockedRoom) {
                if (((LockedRoom) target).openRoom(item)) {
                    this.backpack.removeItem(tempItem);
                    return true;
                }
                else {
                    System.out.println("Can't open room with this item");
                    return false;
                }
            }
            else {
                return false;
            }
        }
    }

}
