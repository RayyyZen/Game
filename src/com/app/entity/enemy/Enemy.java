package com.app.entity.enemy;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.entity.Entity;
import com.app.level.Direction;
import com.app.level.Level;

/**
 * The enemy class that contains his attributes and the total number of enemies that were created
 * @version 5.0 (Fifth world)
 * @since 4.0 (Fourth world)
 * @author Rayane
 */
public abstract class Enemy extends Entity {

    /**
     * The total number of enemies that were created
     */
    protected static int numberOfEnemies = 0;

    /**
     * The damage that the enemy can cause
     */
    private final int damage;
    
    /**
     * The Enemy constructor that takes as an argument a name, a number of hearts, an emoji symbol and the damage he can cause
     * @param name The name of the enemy
     * @param hearts The initial number of hearts of the enemy
     * @param symbol The emoji symbol that represents the enemy
     * @param damage The damage that the enemy can cause
     */
    protected Enemy(String name, int hearts, String symbol, int damage){
        super(name,hearts,symbol);
        this.damage = damage;
        numberOfEnemies++;
    }

    /**
     * Returns the damage that the enemy can cause
     * @return The damage that the enemy can cause
     */
    public int getDamage(){
        return this.damage;
    }

    /**
     * Finds a valid direction where the enemy will move
     * @param level The level where the enemy is located
     * @return A valid direction where the enemy will move
     */
    public abstract Direction getDirection(Level level);

    /**
     * Checks if a cell is valid according to the enemy's possible movements
     * The method is overrided by the enemies that doesn't have the same movement alidation criteria
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the enemy, or false otherwise
     */
    @Override
    public boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY && !cell.containsBox();
    }

    /**
     * Returns a String that contains the enemy's attributes with a certain format
     * @return A String that contains the attributes of the enemy
     */
    @Override
    public String toString(){
        return super.toString() + "\nDamage : " + this.damage;
    }
}