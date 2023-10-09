
/**
 * Items are objects that can be interacted with by the player
 *
 * @author Baldeep Gill
 * @version 03.12.2021
 */
public class Item
{
    private String name;
    private String description;
    private boolean specialItem;
    private boolean storeable;
    private int itemType;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, String description, boolean specialItem, boolean storeable, int itemType)
    {
        this.name = name;
        this.description = description;
        this.specialItem = specialItem;
        this.storeable = storeable;
        this.itemType = itemType;
    }
    
    /**
     * Return name of item as string
     */
    public String getItemName()
    {
        return name;
    }
     /**
      * Return a description of an item as a string
      */
    public String getItemDescription()
    {
        return description;
    }
    
    /**
     * return whether an item is storeable or not
     */
    public boolean isStoreable()
    {
        return storeable;
    }
    
    /**
     * return whether an item is a special item
     */
    public boolean isSpecialItem()
    {
        return specialItem;
    }

    /**
     * return item type id
     */
    public int getItemType()
    {
        return itemType;
    }
}
