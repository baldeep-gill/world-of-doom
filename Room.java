import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 03.12.2021
 */

public class Room
{
    private String description;
    private int blacklist;                      // item type that is blacklisted
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Character> characters;
    
    private Inventory items;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * @param blacklist item type that is blacklisted from entering a room
     */
    public Room(String description, int blacklist) 
    {
        this.description = description;
        this.blacklist = blacklist;
        exits = new HashMap<>();
        items = new Inventory();
        characters = new ArrayList<>();
    }
    
    /**
     * alternate constructor if room doesn't need any blacklisting
     */
    public Room(String description) 
    {
        this.description = description;
        this.blacklist = -1;
        exits = new HashMap<>();
        items = new Inventory();
        characters = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * @return item type that is blacklisted from room
     */
    public int getBlacklist()
    {
        return blacklist;
    }
    
    /**
     * remove an exit from a room
     * @param direction direction of exit that is to be removed from room
     */
    public void removeExit(String direction)
    {
        exits.remove(direction);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Items: torso
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n"  + items.returnItem() + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Adds an item to inventory of room
     * @param Item object being added to room's inventory
     */
    public void addItems(Item item)
    {
        items.addItem(item);
    }
    
    /**
     * Checks to see if specified item is in the room's inventory
     * @param Item you are checking for
     * @return True/False depending on if the item is in the room or not
     */
    public boolean containsItem(Item item)
    {
        return items.containsItem(item);
    }
    
    /**
     * Remove an item from the room's inventory
     * @param Item to remove from inventory
     */
    public void removeItem(Item item)
    {
        items.removeItem(item);
    }
    
    /**
     * Add a character to a room
     * @param character to add to room
     */
    public void addCharacter(Character character)
    {
        characters.add(character);
    }
    
    /**
     * Remove a character from a room
     * @param character to remove from room
     */
    public void removeCharacter(Character character)
    {
        characters.remove(character);
    }
    
    /**
     * Check if room contains a character or not
     * @param character name of character being searched for
     * @return boolean value based on if characater is in room
     */
    public boolean containsCharacter(String character)
    {
        for (Character chars : characters)
        {
            if (chars.getName().equals(character))
            {
                return true;
            }
        }
        return false;
    }
}

