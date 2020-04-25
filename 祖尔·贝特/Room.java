import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

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
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    //store item
    public HashSet<Item> roomItems = new HashSet<Item>();
    public Item myItem;
    public Item myItem2;
    public boolean lock = false;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
    }
    
    /**
     * add item in this room
     */
    public void addItem(String name, int defence, int power, int hp)
    {
        this.myItem = new Item( name, defence, power, hp);
        roomItems.add(this.myItem);
    }
    
    /**
     * add item in this room
     */
    public void addItem2(String name, int defence, int power, int hp)
    {
        this.myItem2 = new Item( name, defence, power, hp);
        roomItems.add(this.myItem2);
    }
    
    /**
     * remove item
     */
    public void removeItem(String name)
    {
        if(name == myItem.name)
        {
            myItem = null;
        }
        else if(name == myItem2.name)
        {
            myItem2 = null;
        }
        else
        {
            System.out.println("The room doesn't contain " + name);
        }
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
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String Description = "You are " + description + ".\n" + getExitString();
        //String item1 = myItem.getItemDescription();
        //String item2 = myItem2.getItemDescription();
        for(Item myItem:roomItems){
            Description += "\n" + myItem.getItemDescription();
        }
        //String myDescription = Description + "\n" + item1 + "\n" + item2;
        return Description;
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
    
    
}

