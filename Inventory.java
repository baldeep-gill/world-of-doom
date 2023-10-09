import java.util.ArrayList;

/**
 * This class adds the functionality to have inventories for different objects
 * Inventories can be limited in size if required
 * Inventories can hold item objects that can be interacted with/used by the player
 *
 * @author Baldeep Gill
 * @version 29.11.2021
 */
public class Inventory
{
    private ArrayList<Item> itemSet;
    private int limit = 0;
    
    /**
     * Constructor for objects of class Inventory
     */
    public Inventory()
    {
        itemSet = new ArrayList<>();
    }
    
    /**
     * Adds the possibility to set a limit on the number of items an object can have in it's inventory
     */
    public Inventory(int limit)
    {
        itemSet = new ArrayList<>();
        this.limit = limit;
    }
    
    /**
     * Add item to inventory
     * @param Item to add to inventory
     */
    public void addItem(Item item)
    {
        itemSet.add(item);
    }
    
    /**
     * Checks to see if the item is contained in an object's inventory
     * @param Item being checked for
     * @return True/False if item is in inventory or not
     */
    public boolean containsItem(Item item)
    {
        if(itemSet.contains(item))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Returns a list of all the items currently in the inventory
     * @return String containing all items in inventory
     */
    public String returnItem()
    {
        String returnString = "Items:";
        for (Item item : itemSet)
        {
            returnString += " " + item.getItemName();
        }
        return returnString;
    }
    
    /**
     * Used to check if an inventory has enough space to store another item
     * @return True/False if inventory has space
     */
    public boolean hasSpace()
    {
        if(limit == 0) // default limit value is 0 if not specified when initialising class = infinite space
        {
            return true;
        }
        else if(itemSet.size() < limit)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Remove an item from inventory
     * @param Item to remove
     */
    public void removeItem(Item item)
    {
        if(itemSet.contains(item))
        {
            itemSet.remove(item);
        }
        else
        {
            System.out.println("Item is not in inventory!");
        }
    }
    
    /**
     * Some special items can grant extra inventory space if picked up. Add to limit by amount specified
     * @param Amount to increase inventory space by
     */
    public void addInventorySpace(int i)
    {
        if(limit == 0)
        {
            System.out.println("Added inventory space!"); // if limit is 0 then inventory is already unlimited in size
        }
        else
        {
            limit += i;
            System.out.println("Added " + i + " inventory space!");
        }
    }
    
    /**
     * Check for any items with a certain itemtype
     * @param itemType type of item being looked for
     * @return true or false if itemtype is in inventory
     */
    public boolean checkForBlacklistedItem(int itemType)
    {
        for (Item item : itemSet)
        {
            if (item.getItemType() == itemType)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Check for number of items that have a certain itemType
     * @param itemType being searched for
     * @return number of items
     */
    public int inventoryTypeCount(int itemType)
    {
        int items = 0;
        for (Item item : itemSet)
        {
            if (item.getItemType() == itemType)
            {
                items++;
            }
        }
        return items;
    }
}