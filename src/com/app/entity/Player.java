package com.app.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.level.Level;
import com.app.usable.Triggerable;
import com.app.usable.Usable;
import com.app.usable.UseMode;
import com.app.usable.item.Item;
import com.app.usable.skill.Lockpicking;
import com.app.usable.skill.Skill;
import com.app.usable.skill.Teleportation;

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

    private boolean canLockpick;

    private int numberOfKills;

    private List<Skill> unlockedSkills;

    /**
     * The maximum number of usables that each player can have at once
     */
    private static final int MAXINVENTORY = 5;

    /**
     * The number of players that were created
     */
    private static int numberOfPlayers = 0;

    /**
     * The number of hearts that each player initially has
     */
    private static final int HEARTS = 5;

    /**
     * The emoji that represents the players
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

        this.unlockedSkills = new ArrayList<>();
        this.unlockedSkills.add(new Teleportation());
        this.unlockedSkills.add(new Lockpicking());

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
     * Returns the number of players created
     * @return The number of players created
     */
    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public int getInventorySize(){
        return this.inventory.size();
    }

    public int getNumberOfKills(){
        return this.numberOfKills;
    }

    public void incrementNumberOfKills(){
        this.numberOfKills++;
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
     * Adds a usable to the player's inventory
     * @param usable The usable that will be added to the player's inventory
     * @return True if the usable could have been added, or false otherwise
     */
    public boolean addUsable(Usable usable){
        if(usable == null){
            throw new IllegalArgumentException("The player tried adding a null Usable !");
        }

        if(this.inventory.size() < MAXINVENTORY){
            this.inventory.add(usable);
            return true;
        }

        return false;
    }

    /**
     * Use an item or a skill from the inventory
     * @param index The index of the usable from the inventory
     * @param level The level where the player is located
     */
    public void use(int index, Level level){
        if(index < 0 || index >= this.inventory.size()){
            throw new IndexOutOfBoundsException("The index of the usable from the inventory is out of bounds !");
        }

        Usable usable = this.inventory.get(index);
        usable.use(level);

        if(usable instanceof Item){
            this.inventory.remove(usable);
        }
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
     * @return A String that contains the attributs of the player
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
            string.append("\n--> " + count + " : " + usable.getClass().getSimpleName());
            count++;
        }

        return string.toString();
    }

    public void activateAutomaticUsables(Level level){
        Iterator<Usable> iterator = this.inventory.iterator();

        while(iterator.hasNext()){
            Usable usable = iterator.next();

            if(usable instanceof Triggerable && ((Triggerable)usable).isTriggerable(level)){
                usable.use(level);
                if(usable instanceof Item){
                    iterator.remove();
                }
            }
        }
    }

    public void canLockpick(){
        this.canLockpick = true;
    }

    public void learnNewSkills(Level level){
        Iterator<Skill> iterator = this.unlockedSkills.iterator();

        while(iterator.hasNext()){
            Skill skill = iterator.next();

            if(skill.condition(this)){
                this.inventory.add(skill);
                if(skill.getUseMode() == UseMode.ONETIME){
                    skill.use(level);
                }
                iterator.remove();
            }
        }
    }
}