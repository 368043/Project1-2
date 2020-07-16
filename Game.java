
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createGame();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createGame()
    {
        Room store, canteen, office1, office2, stockroom, lowStairwell, highStairwell, wc;
        LockedRoom outside;
        Item toothbrush, goldbar;

        //create items
        toothbrush = new Item("toothbrush", 50);
        goldbar = new Item("goldbar", 2500);

        // create the rooms
        store = new Room("the storeroom");
        canteen = new Room("in the company canteen");
        office1 = new Room("in an office");
        office2 = new Room("in an office");
        wc = new Room("wc");
        stockroom = new Room("in the stockroom");
        outside = new LockedRoom("outside the store", toothbrush.getName());
        lowStairwell =  new Room("lower Stairwell");
        highStairwell =  new Room("upper Stairwell");

        // initialise room exits and items
        store.setExits("north", outside);
        store.setExits("south", stockroom);

        stockroom.setExits("north", store);
        stockroom.setExits("east", lowStairwell);
        stockroom.setItems(goldbar);

        lowStairwell.setExits("west", stockroom);
        lowStairwell.setExits("up", highStairwell);

        highStairwell.setExits("west", canteen);
        highStairwell.setExits("down", lowStairwell);

        canteen.setExits("north", office1);
        canteen.setExits("east", highStairwell);
        canteen.setExits("south", wc);
        canteen.setExits("west", office2);

        office1.setExits("south", canteen);

        wc.setExits("north", canteen);
        wc.setItems(toothbrush);

        office2.setExits("east", canteen);
        office2.setExits("west", outside);

        createPlayer(store);  // player starts in store
    }

    /**
     * Creates the player Object with a startRoom
     * @param startRoom The room where the player will start
     */
    private void createPlayer(Room startRoom) {
        this.player = new Player(startRoom);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;

        while (!finished) {

            Command command = parser.getCommand();
            finished = processCommand(command);

        }

        System.out.println("Thank you for playing.  Good bye.");



    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("In this game you need to escape the store within 10 minutes, otherwise the police will catch you.");
        System.out.println("Type '" + CommandWord.HELP.toString() + "' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    private void printLocationInfo(){
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            go(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.LOOK) {
            look(command);
        }
        else if (commandWord == CommandWord.GRAB) {
            grab(command);
        }
        else if (commandWord == CommandWord.BACK) {
            back();
        }
        else if (commandWord == CommandWord.DROP) {
            drop(command);
        }
        else if (commandWord == CommandWord.USE) {
            use(command);
        }
        else if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void go(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        if (this.player.goRoom(direction)) {
            printLocationInfo();
        }
        else {
            System.out.println("There is no door or room is locked. Try to use items to open the door!");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * "Look" was entered. print out details of room.
     */
    private void look(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Look where?");
            return;
        }
        else if (command.getSecondWord().equals("backpack")) {
            System.out.println(player.getItemsString());
        }
        else if (command.getSecondWord().equals("around")) {
            printLocationInfo();
        }

    }

    /**
     * "Grab" was entered. if current room contains item, grab the item.
     * @param command
     */
    private void grab(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to grab...
            System.out.println("Grab what?");
            return;
        }
        String itemName = command.getSecondWord();


        this.player.grabItem(itemName);
    }

    /**
     * Go to previous visited room
     */
    private void back(){
        if (this.player.previousRoom()) {
            printLocationInfo();
        }
        else {
            System.out.println("You can't go back.");
        }
    }

    /**
     * Drop an item of your backpack
     * @param command
     */
    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();

        if (this.player.dropItem(itemName)) {
            System.out.println("Dropped " + itemName);
        }
        else {
            System.out.println("Can't drop " + itemName);
        }
    }

    /**
     * Use an item of your backpack to accomplish something
     * @param command
     */
    private void use(Command command) {
        if(!command.hasThirdWord()) {
            // if there is no second word, we don't know what to use and where...
            System.out.println("Use what and where?");
            return;
        }

        String itemName = command.getSecondWord();
        String direction = command.getThirdWord();

        if (this.player.openRoom(itemName, direction)) {
            System.out.println("It worked!");
        }
        else {
            System.out.println("So, it didn't work...");
        }

    }


    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }

}
