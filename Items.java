import java.util.HashMap;

/**
 * Creates all item objects and places them into maps so that they can be retreived
 *
 * @author Baldeep Gill
 * @version 03.12.2021
 */
public class Items
{
    private HashMap<String, Item> itemTypes;
    private HashMap<Item, String> itemNames;

    /**
     * Constructor for objects of class Items
     */
    public Items()
    {
        itemTypes = new HashMap<>();
        itemNames = new HashMap<>();
        createItems();
    }

    /**
     * Create each item that may be needed in the game
     */
    private void createItems()
    {
        Item head, torso, arm, leg, blood, mop, keycard, backpack, VEGA, furnace;
        
        // item types: 0 = bodypart, 1 = moppable/cleanable objects, 2 = tools, 3 = keycards, 4 = extra inventory space, -1 = characters
        head = new Item("head", "a decapitated head", false, true, 0);
        torso = new Item("torso", "a mangled demon torso", false, true, 0);
        arm = new Item("arm", "a severed demon arm", false, true, 0);
        leg = new Item("leg", "a bloody demon leg", false, true, 0);
        blood = new Item("blood", "a sticky pool of blood", true, false, 1);
        mop = new Item("mop", "a mop that can be used to clean up spillages", true, true, 2);
        keycard = new Item("keycard", "a keycard that can be used to elevate user privileges", true, true, 3);
        backpack = new Item("backpack", "a backpack that can increase inventory space", true, true, 4);
        
        // characters are added so that they can be listed as an item in a room
        VEGA = new Item("VEGA", "", true, false, -1);
        furnace = new Item("furnace", "", true, false, -1);
        
        // place all items into a hashmap so that the object can be returned from name input
        itemTypes.put("head", head);
        itemTypes.put("torso", torso);
        itemTypes.put("arm", arm);
        itemTypes.put("leg", leg);
        itemTypes.put("blood", blood);
        itemTypes.put("mop", mop);
        itemTypes.put("keycard", keycard);
        itemTypes.put("backpack", backpack);
        itemTypes.put("VEGA", VEGA);
        itemTypes.put("furnace", furnace);
        
        // also allow to get the name from inputting an object
        itemNames.put(head, "head");
        itemNames.put(torso, "torso");
        itemNames.put(arm, "arm");
        itemNames.put(leg, "leg");
        itemNames.put(blood, "blood");
        itemNames.put(mop, "mop");
        itemNames.put(keycard, "keycard");
        itemNames.put(backpack, "backpack");
        itemNames.put(VEGA, "VEGA");
        itemNames.put(furnace, "furnace");
    }
    
    /**
     * Return name of an item as string
     */
    public String returnItemName(Item item)
    {
        return itemNames.get(item);
    }
    
    /**
     * Return item object from name
     */
    public Item returnItem(String name)
    {
        return itemTypes.get(name);
    }
}
