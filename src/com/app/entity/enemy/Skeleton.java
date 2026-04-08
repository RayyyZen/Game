package com.app.entity.enemy;

import com.app.level.Direction;
import com.app.level.Level;

/**
 * The skeleton class that contains his attributes
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public class Skeleton extends Enemy {
    
    /**
     * The number of hearts that each skeleton initially has
     */
    private static final int HEARTS = 2;

    /**
     * The symbol the represents the skeletons
     */
    private static final String SYMBOL = "💀";

    /**
     * The number of damage that each skeleton can cause
     */
    private static final int DAMAGE = -3;

    /**
     * The skeleton constructor that takes as an argument only a name
     * @param name The name of the skeleton
     */
    public Skeleton(String name){
        super(name,HEARTS,SYMBOL,DAMAGE);
    }

    /**
     * The skeleton constructor that doesn't take any arguments and automatically generates his name
     */
    public Skeleton(){
        this("Skeleton" + (numberOfEnemies + 1));
    }
    
    /**
     * Finds a valid direction where the skeleton will move
     * The skeleton moves in the direction of the player only if he is one cell away from him
     * @param level The level that the skeleton is located on
     * @return A valid direction where the skeleton will move
     */
    public Direction getDirection(Level level){

        int playerLine = level.getPlayerLine();
        int playerColumn = level.getPlayerColumn();

        int skeletonLine = this.getCurrentLine();
        int skeletonColumn = this.getCurrentColumn();

        int gapLine = playerLine - skeletonLine;
        int gapColumn = playerColumn - skeletonColumn;

        if(Math.abs(gapLine) + Math.abs(gapColumn) <= 1){
            return Direction.NONE;
        }

        int lines = level.getHeight();
        int columns = level.getWidth();

        if(skeletonLine > playerLine && skeletonLine > 0 && level.validCell(this,skeletonLine - 1,skeletonColumn)){
            return Direction.UP;
        
        } else if(skeletonLine < playerLine && skeletonLine < lines - 1 && level.validCell(this,skeletonLine + 1,skeletonColumn)){
            return Direction.DOWN;
        
        } else if(skeletonColumn > playerColumn && skeletonColumn > 0 && level.validCell(this,skeletonLine,skeletonColumn - 1)){
            return Direction.LEFT;
        
        } else if(skeletonColumn < playerColumn && skeletonColumn < columns - 1 && level.validCell(this,skeletonLine,skeletonColumn + 1)){
            return Direction.RIGHT;
        
        }

        return Direction.NONE;
    }
}
