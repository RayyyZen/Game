package com.app.entity.enemy;

import com.app.cell.Cell;
import com.app.level.Direction;
import com.app.level.Level;

/**
 * The ghost class that contains his attributes
 * @version 5.0 (Fifth world)
 * @since 4.0 (Fourth world)
 * @author Rayane
 */
public class Ghost extends Enemy {

    /**
     * The number of hearts that each ghost initially has
     */
    private static final int HEARTS = 1;

    /**
     * The symbol the represents the ghosts
     */
    private static final String SYMBOL = "👻";

    /**
     * The number of damage that each ghost can cause
     */
    private static final int DAMAGE = -1;

    /**
     * The ghost constructor that takes as an argument only a name
     * @param name The name of the ghost
     */
    public Ghost(String name){
        super(name,HEARTS,SYMBOL,DAMAGE);
    }

    /**
     * The ghost constructor that doesn't take any arguments and automatically generates his name
     */
    public Ghost(){
        this("Ghost" + (numberOfEnemies + 1));
    }
    
    /**
     * Finds a valid direction where the ghost will move
     * The ghost moves horizontally until he reaches the player's column then he moves vertically
     * @param level The level where the ghost is located
     * @return A valid direction where the ghost will move
     */
    public Direction getDirection(Level level){

        int playerLine = level.getPlayerLine();
        int playerColumn = level.getPlayerColumn();

        int ghostLine = this.getCurrentLine();
        int ghostColumn = this.getCurrentColumn();

        if(ghostColumn > playerColumn){
            return Direction.LEFT;
        
        } else if(ghostColumn < playerColumn){
            return Direction.RIGHT;

        } else {

            if(ghostLine < playerLine){
                return Direction.DOWN;
            
            } else if(ghostLine > playerLine){
                return Direction.UP;
            }

        }

        return Direction.NONE;
    }
    
    /**
     * Checks if a cell is valid according to the ghost's possible movements
     * The ghost can walk through every cell
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the ghost, or false otherwise
     */
    @Override
    public boolean validMovement(Cell cell){
        return true;
    }
}