import java.util.ArrayList;
import java.util.Random;

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
    private Room main, lab, antechamber, incinerator, corridor, security, cell, bay, chamber, vents;
    private ArrayList<Room> allRooms;
    private Parser parser;
    private Room currentRoom, lastRoom;
    private Inventory playerInventory;
    private Items items;
    private boolean hasMop;
    private boolean hasKeycard;
    private ArrayList<Character> allCharacters;
    private Character vega;
    private Quest vegaKeycard;
    private Character furnace;
    private Quest gameQuest;
    private int amountOfBlood = 0;

    /**
     * Create the game, initialise its internal map, create characters
     */
    public Game() 
    {
        allRooms = new ArrayList<>();
        parser = new Parser();
        playerInventory = new Inventory(3);
        items = new Items();
        hasMop = false;
        createCharacters();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     * Add interactable items to the rooms.
     */
    private void createRooms()
    {
        // create the rooms
        main = new Room("in the main entrance to the facility");
        lab = new Room("a large laboratory full of medical equipment");
        antechamber = new Room("in a room connecting the lab area to the incinerator");
        incinerator = new Room("in a small hot room with a furnace at the opposite end");
        corridor = new Room("in a long corridor with empty prison cells");
        security = new Room("in a small room with video displays of all the other rooms");
        cell = new Room("in a dark dingy cell with just a bed and toilet");
        bay = new Room("at the loading dock that allows the mars facility to resupply");
        chamber = new Room("at the sacrificial chamber used to summon the doom slayer");
        vents = new Room("in a tight cramped air vent", 0); // bodyparts not allowed
        
        allRooms.add(main);
        allRooms.add(lab);
        allRooms.add(incinerator);
        allRooms.add(corridor);
        allRooms.add(security);
        allRooms.add(bay);
        allRooms.add(chamber);
        allRooms.add(vents);
        
        // Add characters to rooms they are in
        incinerator.addCharacter(vega);
        incinerator.addCharacter(furnace);

        // initialise room exits
        // exits that are commented out are exits that are initially closed but can be opened by player's actions
        main.setExit("west", lab);
        main.setExit("south", corridor);
        main.setExit("east", bay);        

        lab.setExit("up", vents);
        //lab.setExit("south", antechamber);
        lab.setExit("east", main);

        incinerator.setExit("up", vents);
        //incinerator.setExit("north", antechamber);

        corridor.setExit("north", main);
        corridor.setExit("east", chamber);
        //corridor.setExit("south", cell);
        //corridor.setExit("west", security);

        security.setExit("up", vents);
        //security.setExit("north", corridor);

        //cell.setExit("north", corridor);

        chamber.setExit("west", corridor);

        bay.setExit("west", main);

        vents.setExit("north", lab);
        vents.setExit("west", incinerator);
        vents.setExit("east", security);

        currentRoom = main;  // start game in main room

        // add items to each room
        bay.addItems(items.returnItem("blood"));
        bay.addItems(items.returnItem("torso"));

        chamber.addItems(items.returnItem("blood"));
        chamber.addItems(items.returnItem("blood"));
        chamber.addItems(items.returnItem("arm"));
        chamber.addItems(items.returnItem("leg"));
        chamber.addItems(items.returnItem("torso"));

        corridor.addItems(items.returnItem("blood"));
        corridor.addItems(items.returnItem("head"));

        cell.addItems(items.returnItem("blood"));
        cell.addItems(items.returnItem("arm"));
        cell.addItems(items.returnItem("leg"));

        lab.addItems(items.returnItem("blood"));
        lab.addItems(items.returnItem("head"));

        incinerator.addItems(items.returnItem("mop"));
        incinerator.addItems(items.returnItem("VEGA"));
        incinerator.addItems(items.returnItem("furnace"));

        antechamber.addItems(items.returnItem("backpack"));

        security.addItems(items.returnItem("keycard"));
    }

    /**
     * Create the characters for the game and initialise their quests
     */
    private void createCharacters()
    {
        allCharacters = new ArrayList<>();

        // create default character settings and add quest
        vega = new Character("VEGA", "Hello, I am VEGA, the sentient intelligence assigned to mars.");
        allCharacters.add(vega);
        String vegaKeycardQuest = "The antechamber that links this room to the lab has been sealed off due to a demonic presence!\nReturn a keycard and I can blow the demons out of the airlock!";
        String vegaKeycardComplete = "Antechamber has been purged of all demonic presence! Feel free to proceed.";
        vegaKeycard = new Quest(0, "Unlock antechamber", vegaKeycardQuest, vegaKeycardComplete, vega, 3);
        vega.addQuest(vegaKeycard);
        
        vegaKeycard.addItemToQuest(items.returnItem("keycard"));

        
        furnace = new Character("furnace", "I am BurnBot3000, you give me the trash, I do the rest");
        allCharacters.add(furnace);
        String burnDemonsQuest = "Bring all demon entrails to me in so that you can meet your quota";
        String burntAllDemons = "You have met your quota, congratulations. See you tomorrow.";
        gameQuest = new Quest(1, "Clean up facility", burnDemonsQuest, burntAllDemons, furnace, 0);
        furnace.addQuest(gameQuest);

        //Adds all bodyparts on floor to quest completion condition
        gameQuest.addItemToQuest(items.returnItem("torso"));
        gameQuest.addItemToQuest(items.returnItem("arm"));
        gameQuest.addItemToQuest(items.returnItem("leg"));
        gameQuest.addItemToQuest(items.returnItem("torso"));
        gameQuest.addItemToQuest(items.returnItem("head"));
        gameQuest.addItemToQuest(items.returnItem("arm"));
        gameQuest.addItemToQuest(items.returnItem("leg"));
        gameQuest.addItemToQuest(items.returnItem("head"));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Doom!");
        System.out.println("World of Doom is a new, incredibly exciting adventure game!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();

        //Nesting if statements was getting annoying...
        switch (commandWord) {
            case "help":
                printHelp();
                break;
            case "go":
                goRoom(command);
                break;
            case "quit":
                wantToQuit = quit(command);
                break;
            case "look":
                look();
                break;
            case "back":
                goBack();
                break;
            case "take":
                takeItem(command);
                break;
            case "inventory":
                showInventory();
                break;
            case "drop":
                dropItem(command);
                break;
            case "mop":
                mopItem(command);
                break;
            case "talk":
                talk(command);
                break;
            case "give":
                giveItem(command);
                break;
        }
        // else command not recognised.
        return wantToQuit;
    }

    /**
     * Checks if character given exists
     * @param character name of character being checked for
     * @return character object being looked for if found, if not, null
     */
    private Character checkForCharacter(String character)
    {
        for (Character npc : allCharacters)
        {
            if(npc.checkForCharacter(character))
            {
                return npc;
            }
        }
        return null;
    }

    /**
     * toggle opening and closing pathways if player has keycard
     * @param hasKeycard global boolean flag for whether player has keycard or not
     */
    private void toggleKeycard(boolean hasKeycard)
    {
        if (hasKeycard)
        {
            corridor.setExit("south", cell);
            corridor.setExit("west", security);
            security.setExit("east", corridor);
            cell.setExit("north", corridor);
            allRooms.add(cell);
        }
        else
        {
            corridor.removeExit("south");
            corridor.removeExit("west");
            security.removeExit("north");
            cell.removeExit("north");
            allRooms.remove(cell);
        }
    }

    /**
     * Checks if player has completed a quest everytime they give something to a character
     * @param npc character object that could have a quest
     */
    private void questComplete(Character npc)
    {
        if (npc.hasQuest() && npc.returnQuest().checkQuestCompletion())
        {
            switch (npc.getQuestID())
            {
                case 0: //Get keycard quest
                    System.out.println(npc.returnQuest().getCompletion());
                    playerInventory.addItem(items.returnItem("keycard"));
                    incinerator.setExit("north", antechamber); //unlocks access to antechamber for player
                    lab.setExit("south", antechamber);
                    antechamber.setExit("north", lab);
                    antechamber.setExit("south", incinerator);
                    allRooms.add(antechamber);
                    break;
                case 1: //Main quest
                    if (amountOfBlood == 6) //Checks if all blood has also been mopped up
                    {
                        System.out.println(npc.returnQuest().getCompletion());
                        gameComplete(); // player has won
                    }
                    break;
            }
            npc.removeQuest();
        }
    }

    /**
     * method called when player has completed main quest
     */
    private void gameComplete()
    {
        //lol imagine
        System.out.println("Congratulations! You completed the game! Feel free to explore more, or, type 'quit' to quit the game.");
    }
    
    /**
     * Check if player is allowed to enter a room with the items they have in their inventory
     * @param nextRoom room player is about to enter
     * @return true or false depending on if they're allowed in or not
     */
    private boolean checkRoomBlacklist(Room nextRoom)
    {
        return playerInventory.checkForBlacklistedItem(nextRoom.getBlacklist());
    }
    
    /**
     * Returns a string value to get the name of an item from it's item id
     * @param type type of item
     * @return string of item type name
     */
    private String getItemType(int type)
    {
        switch (type)
        {
            case 0:
                return "bodyparts";
            case 1:
                return "moppable items";
            case 2:
                return "tools";
            case 3:
                return "keycards";
        }
        return "";
    }

    // implementations of user commands:

    /**
     * Give an item to a character
     * @param command command user has typed in
     */
    private void giveItem(Command command)
    {
        // checking for complete command
        if (!command.hasSecondWord())
        {
            System.out.println("Give what to who?");
        }
        else if (!command.hasThirdWord())
        {
            System.out.println("Give what?");
        }

        String character = command.getSecondWord();
        String item = command.getThirdWord();
        Character npc = checkForCharacter(character);

        if (currentRoom.containsCharacter(character))
        {
            if (playerInventory.containsItem(items.returnItem(item)))
            {
                npc.addItem(items.returnItem(item));
                playerInventory.removeItem(items.returnItem(item));
                System.out.println("Gave " + item + " to " + character + "!");
                questComplete(npc); // checks if player has completed a quest every time they give an item to an npc
            }
            else
            {
                System.out.println("You do not have that item in your inventory!");
            }
        }
        else
        {
            System.out.println("Character is not in this room!");
        }
    }

    /**
     * Talk to a character. Triggers the character's default speech method
     * @param command command player has entered
     */
    private void talk(Command command)
    {
        String character = command.getSecondWord();

        if (!command.hasSecondWord())
        {
            System.out.println("Talk to who?");
            return;
        }

        if (currentRoom.containsCharacter(character))
        {
            System.out.println(checkForCharacter(character).getIntroduction());
        }
        else
        {
            System.out.println("Character is not in this room!");
        }
    }

    /**
     * Mop item command so that they can complete part of their main objective
     * @param command command player has entered
     */
    private void mopItem(Command command)
    {
        String item = command.getSecondWord();
        if (hasMop) // checks if player has a mop
        {
            if (currentRoom.containsItem(items.returnItem(item)))
            {
                if (items.returnItem(item).getItemType() != 1) //Item type 1 is moppable items
                {
                    System.out.println("Item cannot be mopped up!");
                }
                else
                {
                    currentRoom.removeItem(items.returnItem(item));
                    System.out.println("Mopped up " + item + "!");
                    amountOfBlood++; // increment number of blood pools player has mopped
                }
            }
            else
            {
                System.out.println("Item is not in this room!");
            }
        }
        else
        {
            System.out.println("You do not have a mop!");
        }
    }

    /**
     * Drop item on floor. Item is removed from player inventory and added to room inventory so that it persists even if player leaves room
     * @param command command player has entered
     */
    private void dropItem(Command command)
    {
        String item = command.getSecondWord();
        if (playerInventory.containsItem(items.returnItem(item)))
        {
            currentRoom.addItems(items.returnItem(item));
            playerInventory.removeItem(items.returnItem(item));
            System.out.println("Dropped " + item + "!");
            // checks if item dropped is a "special" item type
            if (items.returnItem(item).isSpecialItem())
            {
                // revoke privileges if so
                switch (items.returnItem(item).getItemType())
                {
                    case (2):
                        hasMop = false;
                        break;
                    case (3):
                        toggleKeycard(false);
                        break;
                }
            }
        }
        else
        {
            System.out.println("You do not have that item in your inventory!");
        }
    }

    /**
     * Displays inventory to user
     */
    private void showInventory()
    {
        System.out.println(playerInventory.returnItem());
    }

    /**
     * Functionality for players to pick items up off the ground
     * If item listed doesn't exist print an error
     * @param command command player has entered
     */
    private void takeItem(Command command)
    {
        String item = command.getSecondWord();
        if (currentRoom.containsItem(items.returnItem(item)))
        {
            if (items.returnItem(item).isStoreable())
            {
                if (playerInventory.hasSpace())
                {
                    playerInventory.addItem(items.returnItem(item));
                    currentRoom.removeItem(items.returnItem(item));
                    System.out.println("Took " + items.returnItem(item).getItemDescription() + "!");
                    if (items.returnItem(item).isSpecialItem())
                    {
                        // grants special status to player
                        switch (items.returnItem(item).getItemType())
                        {
                            case (4): //backpack (any item that adds inventory space)
                                playerInventory.removeItem(items.returnItem(item));
                                playerInventory.addInventorySpace(2);
                                break;
                            case (2):
                                hasMop = true;
                                break;
                            case (3):
                                toggleKeycard(true);
                                break;
                        }
                    }
                }
                else
                {
                    System.out.println("You do not have enough space in your inventory!");
                }
            }
            else
            {
                System.out.println("Can't take that item!");
            }
        }
        else
        {
            System.out.println("Room does not have that item!");
        }
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are a janitor at the UAC.");
        System.out.println("You are to clean up the mess the Slayer left behind.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Displays long description of current room
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        lastRoom = currentRoom; // when you go somewhere, remember the room you were just in
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // if in main room and user types "go random", game will teleport them to a random room
        if ((direction.equals("random")) && (currentRoom == main))
        {
            Random rand = new Random();
            int index = rand.nextInt(allRooms.size());
            currentRoom = allRooms.get(index);
            System.out.println(currentRoom.getLongDescription());
            return;
        }
        
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) 
        {
            System.out.println("There is no door!");
        }
        else 
        {
            // check if we are allowed to move into the next room with the items in our inventory
            if (checkRoomBlacklist(nextRoom))
            {
                System.out.println("Cannot move to next room while carrying " + getItemType(nextRoom.getBlacklist()));
            }
            else
            {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }

    /**
     * return player to previous room they were in
     */
    private void goBack()
    {
        Room nextRoom = lastRoom;

        lastRoom = currentRoom;
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
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
}
