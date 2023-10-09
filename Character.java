import java.util.HashMap;
import java.util.ArrayList;

/**
 * characters can be interacted with by the player and can set the player with quests
 *
 * @author Baldeep Gill
 * @version 03.12.2021
 */
public class Character
{
    private String name;
    private String introduction;
    private Inventory inventory;
    private Quest newQuest;

    /**
     * Constructor for objects of class Character
     */
    public Character(String name, String introduction)
    {
        this.name = name;
        this.introduction = introduction;
        inventory = new Inventory();
        newQuest = null;
    }
    
    /**
     * @return name of character
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * add item to characters inventory
     * @param item item object to be added to npc inventory
     */
    public void addItem(Item item)
    {
        inventory.addItem(item);
    }
    
    /**
     * remove item from character inventory
     * @param item item object to remove from npc inventory
     */
    public void removeItem(Item item)
    {
        inventory.removeItem(item);
    }
    
    /**
     * @return basic introduction message from character
     */
    public String getIntroduction()
    {
        if (newQuest == null)
        {
            return introduction;
        }
        else
        {
            // if character has a quest return the quest description on a new line
            return introduction + "\n" + newQuest.getDescription();
        }
    }
    
    /**
     * Add a quest to a character
     * @param quest quest object to assign to this character
     */
    public void addQuest(Quest quest)
    {
        newQuest = quest;
    }
    
    /**
     * @return whether the character has a quest or not
     */
    public boolean hasQuest()
    {
        if (newQuest != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * reset character's quest
     */
    public void removeQuest()
    {
        newQuest = null;
    }
    
    /**
     * @return quest object assigned to character
     */
    public Quest returnQuest()
    {
        return newQuest;
    }
    
    /**
     * @return questid of quest assigned to character
     */
    public int getQuestID()
    {
        return newQuest.getQuestID();
    }
    
    /**
     * @param itemType type of item being searched for
     * @return number of itemtypes in character inventory
     */
    public int inventoryTypeCount(int itemType)
    {
        return inventory.inventoryTypeCount(itemType);
    }
    
    /**
     * check if character is valid
     */
    public boolean checkForCharacter(String searchString)
    {
        if (name.equals(searchString))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
