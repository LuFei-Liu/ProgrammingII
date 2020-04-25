
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Item
{
    // instance variables - replace the example below with your own
    public String name;
    private int defence;
    private int power;
    private int hp;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name)
    {
        // initialise instance variables
        this.name = name;
        this.defence = 0;
        this.power = 0;
        this.hp = 0;
    }
    
    public Item(String name, int defence, int power, int hp)
    {
        // initialise instance variables
        this.name = name;
        this.defence = defence;
        this.power = power;
        this.hp = hp;
    }
    
    /**
     * Return a description of the item in the room:
     * @return A description of the item in this room
     */
    public String getItemDescription()
    {
        return "The room contains " + this.name;
    }
    
    /**
     * add power
     */
    public void addPower(int num)
    {
        this.power += num;
    }
    
    /**
     * add defence
     */
    public void addDefence(int num)
    {
        this.defence += num;
    }
    
    /**
    * add Hp
    */
    public void addHp(int num)
    {
        this.hp += num;
    }
}
