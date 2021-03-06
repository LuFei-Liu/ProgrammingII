import java.util.HashSet;
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
    //语法分析程序
    private Parser parser;
    private Room currentRoom , dragonRoom , bossRoom , home;
    private HashSet<Item> playerSet = new HashSet<Item>();
    //if you walk more than 20 steps,you will lost the game.
    private int step;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        step = 0;
        parser = new Parser();
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room  fishRoom , slimeRoom , pigRoom , skullRoom ;
        Room shop , hospital , rewardRoom , loft , basement;
      
        // create the rooms
        home = new Room("in a warm home. The start of the adventure");
        
        //creat loft and key
        loft = new Room("in a dark loft. There is a gleam of light in the darkness");
        loft.addItem("key",0,0,0);
        //add item//Defence power hp
        home.addItem("letter",0,0,9);
        home.addItem2("a old shoes",3,1,9);
        
        shop = new Room("in a shop");
        shop.addItem("new clothes  ",4,1,9);
        shop.addItem2("sword ",0,8,9);
        
        hospital = new Room("in the hospital");
        hospital.addItem("pills ",1,1,1);
        hospital.addItem2("new shoes ",1,1,1);
        
        rewardRoom = new Room("in a rewardRoom");
        rewardRoom.addItem("money",1,1,1);
        rewardRoom.addItem2("IronSword ",1,1,1);
        
        dragonRoom = new Room("in the dragonRoom");
        dragonRoom.addItem("dragon_baby ",1,1,1);
        dragonRoom.addItem2("dragon_mother ",1,1,1);
        
        fishRoom = new Room("in the fishRoom");
        fishRoom.addItem("fish_baby",1,1,1);
        fishRoom.addItem2("fish_mother ",1,1,1);
        
        slimeRoom = new Room("in the slimeRoom");
        slimeRoom.addItem("slime_baby",1,1,1);
        slimeRoom.addItem2("slime_mother",1,1,1);
        
        pigRoom = new Room("in the pigRoom");
        pigRoom.addItem("pig_baby",1,1,1);
        pigRoom.addItem2("pig_mother",1,1,1);
        
        skullRoom = new Room("in the skullRoom");
        skullRoom.addItem("skull_baby",1,1,1);
        skullRoom.addItem2("skull_mother",1,1,1);
        
        bossRoom = new Room("in the bossRoom");
        bossRoom.addItem("boss_1",1,1,1);
        bossRoom.addItem2("boss_2",1,1,1);
        
        // initialise room exits
        loft.setExit("down",home);
        home.setExit("up",loft);
        //1L
        shop.setExit("south", slimeRoom);
        slimeRoom.setExit("north", shop);
        
        slimeRoom.setExit("south", rewardRoom);
        rewardRoom.setExit("north", slimeRoom);
        //2L
        home.setExit("south", dragonRoom);
        dragonRoom.setExit("north", home);
        //lock the door
        dragonRoom.lock = true;
        
        dragonRoom.setExit("south", skullRoom);
        skullRoom.setExit("north", dragonRoom);
        
        skullRoom.setExit("south", pigRoom);
        pigRoom.setExit("north", skullRoom);
        //3L
        fishRoom.setExit("south", hospital);
        hospital.setExit("north", fishRoom);
        
        hospital.setExit("south", bossRoom);
        bossRoom.setExit("north", hospital);
        //1Line
        shop.setExit("east", dragonRoom);
        dragonRoom.setExit("west", shop);
        
        dragonRoom.setExit("east", fishRoom);
        fishRoom.setExit("west", dragonRoom);
        //2Line
        slimeRoom.setExit("east", skullRoom);
        skullRoom.setExit("west", slimeRoom);
        
        skullRoom.setExit("east", hospital);
        hospital.setExit("west", skullRoom);
        //3Line
        rewardRoom.setExit("east", pigRoom);
        pigRoom.setExit("west", rewardRoom);
        
        pigRoom.setExit("east", bossRoom);
        bossRoom.setExit("west", pigRoom);
        //special Room
        basement = new Room("in the cold basement");
        rewardRoom.setExit("down", basement);
        basement.setExit("up", rewardRoom);
        basement.addItem("Magic_Portals",1,1,1);
        
        currentRoom = home;  // start game outside
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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
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
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("take")){
            takeItems();
            System.out.println(currentRoom.getLongDescription());
        }
        else if(commandWord.equals("inventory")){
            showInventory();
            System.out.println(currentRoom.getLongDescription());
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if(commandWord.equals("use")){
            useItem(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * drop the item.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know drop what...
            System.out.println("drop what?");
            return;
        }

        String dropItem = command.getSecondWord();

        // Try to drop the item.
        boolean sign = false;
        for(Item uselessItem:playerSet){
            if(currentRoom.roomItems.size() < 2){
                //System.out.println(dropItem);
                //System.out.println(uselessItem.name);
                
                if(uselessItem.name.equals(dropItem)){
                    playerSet.remove(uselessItem);
                    sign = true;
                    currentRoom.roomItems.add(uselessItem);
                    break;
                }
            }
            else{
                System.out.println("The Room already have two item");
            }
        }
        //check whether drop successfully
        if(sign)
        {
            System.out.println("You droped the " + dropItem);
        }
        else{
            System.out.println("You don't have " + dropItem);
        }
        
    }
    
    /**
     * show the player's Inventory
     */
    private void showInventory()
    {
        System.out.println("You have : ");
        for(Item playerItem:playerSet){
            System.out.print(playerItem.name + ",");
        }
        System.out.println("\n");
    }
    
    /**
     * Unlock the door
     */
    private void unlockRoom(Room lockRoom)
    {
        for(Item myKey:playerSet){
            if(myKey.name.equals("key")){
                lockRoom.lock = false;
                
            }
        }
        System.out.println("You insert the key and the door open! ^_^");
    }
    
    /**
     * use item.
     */
    private void useItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("use What?");
            return;
        }
        String secondWord = command.getSecondWord();
        
        boolean ifYouHaveKey = false;
        boolean ifYouHaveTp = false;
        
        for(Item myKey:playerSet){
            if(myKey.name.equals("key")){
                ifYouHaveKey = true;
            }
            if(myKey.name.equals("Magic_Portals")){
                ifYouHaveTp = true;
            }
        }
        
        if(secondWord.equals("key")){
            if(ifYouHaveKey && currentRoom == home)
            {
                unlockRoom(dragonRoom);
            }
            else{
                System.out.println(secondWord + "doesn't work");
            }
        }
        
        if(secondWord.equals("Magic_Portals")){
            if(ifYouHaveTp && currentRoom == bossRoom){
                System.out.println("Win");
            }
            else{
                System.out.println(secondWord + "doesn't work");
            }
        }
    }
    
    /**
     * take items and add it into Player's set.
     */
    private void takeItems() 
    {
        if(currentRoom.roomItems.isEmpty()){
            System.out.println("no item");
        }
        else{
            for(Item myItem:currentRoom.roomItems){
                playerSet.add(myItem);
                System.out.println("you got the " + myItem.name + ".");
            }
            currentRoom.roomItems.clear();
        }

    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the strange world.");
        System.out.println("You need to find the Magic_Portals and use it to go bakc the real word");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if(nextRoom.lock){
            System.out.println("The door had been locked. You need to find the key.");
        }
        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            step ++;
            if(step >=20){
                System.out.println("You Lost");
            }
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
