package com.app.entity.enemy;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.entity.Entity;
import com.app.level.*;

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
     * The Enemy constructor that takes as an argument a name and a number of hearts
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
     * @param level The level that the enemy is located on
     * @return A valid direction where the enemy will move
     */
    public abstract Direction getDirection(Level level);

    /**
     * Checks if a cell is valid according to the enemy's possible movements
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the enemy, or false otherwise
     */
    @Override
    public boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY && !cell.containsBox();
    }
}