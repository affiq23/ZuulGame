
/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initializes all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Game {

    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;

    /**
     * Create the game and initialize its internal map.
     */
    public Game() {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room start, yellowCave, blueCave, redCave, dungeon1, dungeon2, mainExit;

        // create the rooms
        start = new Room("inside the starting cave. This is a small, well-lit room that has many cabinets along the walls. "
                + "From here you can continue your adventure.");

        yellowCave = new Room("inside the Yellow Cavern. An amazing golden shine illuminates the cave. "
                + "There might be a jewel here.");

        blueCave = new Room("inside the Blue Cavern."
                + " It is shadowy and eerie, with a dull blue light coming from the ceiling. There might be a jewel here.");

        redCave = new Room("inside the Red Cavern."
                + "You see a wall of fire surrounding the edges of the room, lighting the whole cavern with a red hue. "
                + "There might be a jewel here.");

        dungeon1 = new Room("inside Dungeon #1. It is a dark and damp dungeon, and you can hear mumblings in the room. "
                + "The floor and walls have moss growing on them, and you can see strange writing along the walls as well. "
        );

        dungeon2 = new Room("inside Dungeon #2. It is brilliantly lit and you see a library containing a wide array of books. "
                + "The walls and floor are spotless and have an intricate design etched into them. "
        );

        mainExit = new Room("inside the End Cavern! You open the door with the ring you have and find a room filled with ominous writing. "
                + "You, being sick of strange writing in rooms, decide to leave using the tunnel stretching upwards. ");

        // initialise room exits
        start.setExits("north", yellowCave);
        start.setExits("east", blueCave);

        yellowCave.setExits("north", dungeon1);
        yellowCave.setExits("south", start);

        blueCave.setExits("east", dungeon2);
        blueCave.setExits("west", start);

        redCave.setExits("north", mainExit);
        redCave.setExits("south", dungeon2);

        dungeon1.setExits("south", yellowCave);
        dungeon2.setExits("north", redCave);
        dungeon2.setExits("east", blueCave);
        mainExit.setExits(" ", null);

        currentRoom = start;  //start game in the starting room

        Item redJewel = new Item("Red Jewel", "this jewel can be traded for something particular");
        Item blueJewel = new Item("Blue Jewel", "this jewel can be traded for something particular");
        Item yellowJewel = new Item("Yellow Jewel", "this jewel can be traded for something particular");
        Item redKey = new Item("Red key", "This key may be able to open a door");
        Item yellowKey = new Item("Yellow key", "This key may be able to open a door");
        Item blueKey = new Item("Blue key", "This key may be able to open a door");

        redCave.addItem("Red jewel", redJewel);
        blueCave.addItem("Blue jewel", blueJewel);
        yellowCave.addItem("Yellow jewel", yellowJewel);
        redCave.addItem("Red key", redKey);
        blueCave.addItem("Blue key", blueKey);
        yellowCave.addItem("Yellow key", yellowKey);
    }

    /**
     * Main play routine. Loops until end of play.
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
        System.out.println("Come back to the cave when you are ready.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to Subterranean!");
        System.out.println("This is an exciting game in which you search for treasure deep within a cave.");
        System.out.println("Type 'guide' if you need help.");
        System.out.println();
        printLocationInfo();

    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("guide")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("eat")) {
            eat();
        } else if (commandWord.equals("back")) {
            goBack(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("Hi there. I'm your guide.");
        System.out.println("You're searching in the cave for the treasure.");
        System.out.println("What direction do you want to go to next?");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {

        if (!command.hasSecondWord()) {

            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There's no room to go back to!");

        } else {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * Enters the specified room and prints the description.
     */
    private void enterRoom(Room nextRoom) {
        previousRoom = currentRoom;
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());

    }

    /**
     * Go back to the previous room.
     */
    private void goBack(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Only type 'back' if you want to go back.");
            return;
        }
        if (previousRoom == null) {
            System.out.println("There's no room to go back to!");
        } else {
            enterRoom(previousRoom);
        }
    }

    private void printLocationInfo() {

        System.out.println(currentRoom.getLongDescription());
        System.out.println();

    }

    private void look() {
        System.out.println(" ");
        
        System.out.println(currentRoom.getItemString());
        System.out.println(currentRoom.getItemDescription());
        System.out.println(" ");

    }
    
    private void take() {
  
        
        
    }

    private void eat() {
        System.out.println("You have already eaten. You are not hungry at this time.");
    }
    
   
    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }
}
