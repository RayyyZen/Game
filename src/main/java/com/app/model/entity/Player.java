package com.app.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.app.model.cell.Cell;
import com.app.model.cell.CellType;
import com.app.model.level.Level;
import com.app.model.usable.*;
import com.app.model.usable.item.Item;
import com.app.model.usable.skill.*;

/**
 * The player class that contains his attributes and the total number of players that were created
 * @version 5.0 (Fifth world)
 * @since 1.0 (First world)
 * @author Rayane
 */
public class Player extends Entity {

    /**
     * The current score of the player (it is always a positive integer)
     */
    private int score;

    /**
     * The inventory of the player that contains usables (items or skills)
     */
    private List<Usable> inventory;

    /**
     * The maximum number of usables that each player can have at once
     */
    private static final int MAXINVENTORY = 5;

    /**
     * Indicates if the player can open locked doors or not
     */
    private boolean canLockpick;

    /**
     * The number of kills of the player
     */
    private int numberOfKills;

    /**
     * The number of swords the player used
     */
    private int numberOfUsedSwords;

    /**
     * The skills that the player didn't unlock yet
     */
    private List<Skill> unlockedSkills;

    /**
     * The number of players that were created
     */
    private static int numberOfPlayers = 0;

    /**
     * The number of hearts that each player initially has
     */
    private static final int HEARTS = 5;

    /**
     * The emoji symbol that represents each player
     */
    private static final String SYMBOL = "🐱‍👤";

    /**
     * The player constructor that takes as an argument only a name
     * @param name The name of the player
     */
    public Player(String name){
        super(name,HEARTS,SYMBOL);
        this.score = 0;
        this.inventory = new ArrayList<>();
        this.canLockpick = false;
        this.numberOfKills = 0;
        this.numberOfUsedSwords = 0;

        this.unlockedSkills = new ArrayList<>();
        this.unlockedSkills.add(new Teleportation());
        this.unlockedSkills.add(new Lockpicking());
        this.unlockedSkills.add(new Bomb());

        numberOfPlayers++;
    }

    /**
     * The player constructor that doesn't take any arguments and automatically generates his name
     */
    public Player(){
        this("Player" + (numberOfPlayers + 1));
    }

    /**
     * Returns the score of the player
     * @return The score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Returns the player's inventory size
     * @return The player's inventory size
     */
    public int getInventorySize(){
        return this.inventory.size();
    }

    /**
     * Returns the player's number of kills
     * @return The player's number of kills
     */
    public int getNumberOfKills(){
        return this.numberOfKills;
    }

    /**
     * Returns the number of swords the player used
     * @return The number of swords the player used
     */
    public int getNumberOfUsedSwords(){
        return this.numberOfUsedSwords;
    }

    /**
     * Returns the number of players created
     * @return The number of players created
     */
    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Either adds or substracts a score from the player's current score
     * If the score is positive it will be added to the player and if it is negative it will be deducted from him
     * @param score The score that will be added to or deducted from the player's score, depending on its sign
     */
    public void modifyScore(int score){
        this.score = super.modifyNumber(this.score,score);
    }

    /**
     * Gives the player the ability to open locked doors
     */
    public void canLockpick(){
        this.canLockpick = true;
    }

    /**
     * Increments by one the number of kills that the player has
     */
    public void incrementNumberOfKills(){
        this.numberOfKills++;
    }

    /**
     * Increments by one the number of swords that the player used
     */
    public void incrementNumberOfUsedSwords(){
        this.numberOfUsedSwords++;
    }

    /**
     * Adds a usable to the player's inventory
     * @param usable The usable that will be added to the player's inventory
     * @return True if the usable could have been added, or false otherwise
     */
    public boolean addUsable(Usable usable){
        if(usable == null){
            throw new IllegalArgumentException("The player tried adding a null usable");
        }

        if(this.inventory.size() < MAXINVENTORY){
            this.inventory.add(usable);
            Collections.sort(this.inventory,new UsableComparator());
            return true;
        }

        return false;
    }

    /**
     * Use an item or a skill from the inventory
     * @param index The index of the usable from the inventory
     * @param level The level where the player is located
     * @return A list of the enemies that were damaged by the player while using the item or skill
     */
    public List<String> use(int index, Level level){
        if(index < 0 || index >= this.inventory.size()){
            throw new IndexOutOfBoundsException("The index of the usable from the inventory that the player wants to use is out of bounds");
        }

        Usable usable = this.inventory.get(index);
        usable.use(level);

        List<String> damagedEnemies = null;
        if(usable instanceof Weapon){
            damagedEnemies = (((Weapon) usable).getDamagedEnemies());
        }

        if(usable instanceof Item){
            this.inventory.remove(usable);
        }
        // Removes the usable from the inventory in case it is an item

        return damagedEnemies;
    }

    /**
     * Activates all the automatic usables contained in the player's inventory
     * @param level The level where the player is located
     * @return A list of the enemies that were damaged by the player while using the automatic items or skills
     */
    public List<String> activateAutomaticUsables(Level level){
        List<String> damagedEnemies = new ArrayList<>();

        Iterator<Usable> iterator = this.inventory.iterator();

        while(iterator.hasNext()){
            Usable usable = iterator.next();

            if(usable instanceof Triggerable && ((Triggerable) usable).shouldTrigger(level)){
                usable.use(level);
                if(usable instanceof Weapon){
                    damagedEnemies.addAll(((Weapon) usable).getDamagedEnemies());
                }

                if(usable instanceof Item){
                    iterator.remove();
                }
            }
        }

        return damagedEnemies;
    }

    /**
     * Unlocks new skills according to the player's progress
     * @param level The level where the player is located
     * @return A list of the names of the new skills that the player unlocked
     */
    public List<String> unlockNewSkills(Level level){
        List<String> newSkills = new ArrayList<>();

        Iterator<Skill> iterator = this.unlockedSkills.iterator();

        while(iterator.hasNext()){
            Skill skill = iterator.next();

            if(this.inventory.size() < MAXINVENTORY && skill.conditionToUnlock(this)){
                this.inventory.add(skill);
                if(skill.shouldTrigger(level)){
                    skill.use(level);
                }
                newSkills.add(skill.getName());
                iterator.remove();
            }
        }

        return newSkills;
    }

    /**
     * Checks if a cell is valid according to the player's possible movements
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the player, or false otherwise
     */
    @Override
    public boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY || type == CellType.TRAP || (type == CellType.LOCKED_DOOR && this.canLockpick);
    }

    /**
     * Returns a String that contains the player's attributes with a certain format
     * @return A String that contains the attributes of the player
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        String stringScore = "pt";

        if(this.score > 1){
            stringScore += "s";
        }

        string.append(super.toString() + "\nScore : " + this.score + " " + stringScore + "\nNumber of kills : " + this.numberOfKills + "\nInventory : ");

        if(this.inventory.isEmpty()){
            return string.toString() + "Empty";
        }

        int count = 1;
        for(Usable usable : this.inventory){
            string.append("\n--> " + count + " : " + usable.getName());
            count++;
        }

        return string.toString();
    }

    public String getUsableSymbol(int index){
        return this.inventory.get(index).getSymbol();
    }
}