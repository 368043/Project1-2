import java.util.Stack;

public class Player {

    private Backpack backpack;
    private Room currentRoom;
    private Stack<Room> roomHistory;

    public Player(Room startRoom){
        this.backpack = new Backpack();
        this.currentRoom = startRoom;
        roomHistory = new Stack<>();
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

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

    public String getItemsString() {
        return this.backpack.getItemsString();
    }

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

    public boolean previousRoom() {
        if (roomHistory.empty()) {
            return false;
        }
        else {
            setCurrentRoom(roomHistory.pop());
            return true;
        }
    }

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
