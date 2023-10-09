import java.util.ArrayList;

/**
 * quests are special tasks that the player can undertake in order to receive some sort of reward 
 *
 * @author Baldeep Gill
 * @version 03.12.2021
 */
public class Quest
{
    private int questID;
    private String name;
    private String description;
    private String questComplete;
    private int objectiveItemType; //item type quest is looking for
    private ArrayList<Item> questCondition;
    private Character character;
    private String returnItem;

    /**
     * Constructor for objects of class Quest
     */
    public Quest(int questID, String name, String description, String questComplete, Character character, int objectiveItemType)
    {
        this.questID = questID;
        this.name = name;
        this.description = description;
        this.questComplete = questComplete;
        this.character = character;
        this.objectiveItemType = objectiveItemType;
        this.returnItem = returnItem;
        questCondition = new ArrayList<>();
    }
    
    /**
     * @return description of quest
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @return string when you have finished the quest
     */
    public String getCompletion()
    {
        return questComplete;
    }
    
    /**
     * add an item to the quest objective
     * @param item one item object that needs to be handed back to complete
     */
    public void addItemToQuest(Item item)
    {
        questCondition.add(item);
    }
    
    /**
     * @return quest id
     */
    public int getQuestID()
    {
        return questID;
    }
    
    /**
     * @return items required to complete quest
     */
    public String getQuestCondition()
    {
        String returnString = "";
        String questItems = "";
        for (Item item : questCondition)
        {
            questItems += item.getItemName() + " ";
        }
        return (returnString + "Return " + questItems + " to " + character.getName());
    }
    
    /**
     * check if quest has been completed or not. check quest givers inventory if they have all the items stated in the
     * quest condition list
     * this method can only really check if the same number of item types are in each inventory and not each explicit item itself
     * @return true or false if quest has been completed or not
     */
    public boolean checkQuestCompletion()
    {
        int charCount = 0;
        int questCount = 0;
        for (Item item : questCondition)
        {
            if (item.getItemType() == objectiveItemType)
            {
                questCount++;
            }
        }
        
        charCount = character.inventoryTypeCount(objectiveItemType);
        
        if (charCount == questCount)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
