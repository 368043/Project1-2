import java.util.ArrayList;

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
    private Timer timer;
    private Room winScenario;

    /**
     * Create the game and initialise its internal map.
     */
    public Game(int minutes)
    {
        createGame();
        this.timer = new Timer(minutes);
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createGame()
    {
        Room store, canteen, office1, office2, stockroom, lowStairwell, highStairwell, wc;
        LockedRoom outside;
        Item toothbrush, goldBar, cash, knife, noteBook, pencil, safe, plate, spoon, cup, lockPicker, key;
        Workbench workbench1, workbench2;

        //create items
        toothbrush = new Item("toothbrush", 50);
        goldBar = new Item("goldbar", 700);
        cash = new Item("cash", 200);
        knife = new Item("knife", 200);
        noteBook = new Item("notebook", 100);
        pencil = new Item("pencil", 20);
        safe = new Item("safe", 500);
        plate = new Item("plate", 150);
        spoon = new Item("spoon", 80);
        cup = new Item("cup", 60);
        lockPicker = new Item("lockpicker", 80);
        key = new Item("key", 50);


        //create components
        ArrayList<String> cashComponents = new ArrayList<>();
        cashComponents.add("safe");
        cashComponents.add("key");

        ArrayList<String> lockPickComponents = new ArrayList<>();
        lockPickComponents.add("toothbrush");
        lockPickComponents.add("knife");

        //create workbenches
        workbench1 = new Workbench(cashComponents, cash);
        workbench2 = new Workbench(lockPickComponents, lockPicker);

        // create the rooms
        store = new Room("in the storeroom");
        canteen = new Room("in the company canteen");
        office1 = new Room("in an office");
        office2 = new Room("in an office");
        wc = new Room("wc");
        stockroom = new Room("in the stockroom");
        outside = new LockedRoom("outside the store", lockPicker.getName());
        lowStairwell =  new Room("in the lower Stairwell");
        highStairwell =  new Room("in the upper Stairwell");

        // initialise room exits and items
        store.setExits("north", outside);
        store.setExits("south", stockroom);
        store.setItems(noteBook);
        store.setItems(pencil);
        store.setItems(safe);

        stockroom.setExits("north", store);
        stockroom.setExits("east", lowStairwell);
        stockroom.setItems(goldBar);

        lowStairwell.setExits("west", stockroom);
        lowStairwell.setExits("up", highStairwell);

        highStairwell.setExits("west", canteen);
        highStairwell.setExits("down", lowStairwell);
        highStairwell.setItems(knife);

        canteen.setExits("north", office1);
        canteen.setExits("east", highStairwell);
        canteen.setExits("south", wc);
        canteen.setExits("west", office2);
        canteen.setItems(plate);
        canteen.setItems(spoon);
        canteen.setItems(cup);
        canteen.setWorkbench(workbench2);

        office1.setExits("south", canteen);
        office1.setItems(key);

        wc.setExits("north", canteen);
        wc.setItems(toothbrush);

        office2.setExits("east", canteen);
        office2.setExits("west", outside);
        office2.setWorkbench(workbench1);

        createPlayer(store);  // player starts in store
        this.winScenario = outside;
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

        this.timer.start();

        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;

        while (!finished && !checkWin() && !timer.check()) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
        if (checkWin()){
            System.out.println("You did it!\nYou escaped the store before the police came.\nWell done and enjoy your loot.");
        }
        else if (timer.check()){
            System.out.println("HANDS UP! THIS IS THE POLICE. YOU ARE SURROUNDED.\nYou failed to escape the store.\nTry again when you out of jail.");
        }

        System.out.println("Thank you for playing. Good bye!");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");

    }

    /**
     * Checks if game has been won
     * @return true or false
     */
    private boolean checkWin(){
        if (this.player.getCurrentRoom() == this.winScenario){
            return true;
        }else {
            return false;
        }
    }

    private void printTime(){
        System.out.println(this.timer.getLeftTime());
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Welcome to the Robber Game");
        System.out.println("In this game you need to escape the store ASAP,\notherwise the police will catch you.");
        System.out.println("Type '" + CommandWord.HELP.toString() + "' if you need help.");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");

        System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
        printTime();
        printLocationInfo();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
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

        switch (commandWord){
            case HELP:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                printHelp();
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case GO:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                go(command);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case USE:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                use(command);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case BACK:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                back();
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case DROP:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                drop(command);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case GRAB:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                grab(command);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case LOOK:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                look(command);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            case BUILD:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                build(command);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
            case UNKNOWN:
                System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++");
                printTime();
                System.out.println("\nI don't know what you mean...\n");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++\n");
                break;
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
        System.out.println("Try to get out of the store as soon as possible.");
        System.out.println("Available command words are:");
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
            System.out.println("\nGo where?\n");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        if (this.player.goRoom(direction)) {
            printLocationInfo();
        }
        else {
            System.out.println("\nIs not possible.\n");
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
            System.out.println("\nQuit what?\n");
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
            System.out.println("\nLook where?\n");
            return;
        }

        switch (command.getSecondWord()){
            case "backpack":
                System.out.println(player.getItemsString());
                break;
            case "around":
                printLocationInfo();
                break;
            case "workbench":

                player.lookWorkbench();

                break;
        }
    }

    /**
     * "Grab" was entered. if current room contains item, grab the item.
     * @param command
     */
    private void grab(Command command){
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to grab...
            System.out.println("\nGrab what?\n");
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
            System.out.println("\nYou can't go back.\n");
        }
    }

    /**
     * Drop an item of your backpack
     * @param command
     */
    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("\nDrop what?\n");
            return;
        }

        String itemName = command.getSecondWord();

        if (this.player.dropItem(itemName)) {
            System.out.println("\nDropped " + itemName + "\n");
        }
        else {
            System.out.println("\nCan't drop " + itemName + "\n");
        }
    }

    /**
     * Use an item of your backpack to accomplish something
     * @param command
     */
    private void use(Command command) {
        if(!command.hasThirdWord()) {
            // if there is no second word, we don't know what to use and where...
            System.out.println("\nUse what and where?\n");
            return;
        }

        String itemName = command.getSecondWord();
        String direction = command.getThirdWord();

        if (this.player.openRoom(itemName, direction)) {
            System.out.println("\nIt worked!\n");
        }
        else {
            System.out.println("\nSo, it didn't work...\n");
        }

    }

    /**
     * Build an item with your collected items at a workbench
     * @param command
     */
    private void build(Command command){
        if (!command.hasSecondWord()){
            System.out.println("\nBuild what?\n");
            return;
        }

        String itemName = command.getSecondWord();

        this.player.buildItem(itemName);
    }

}
