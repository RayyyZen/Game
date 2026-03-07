package com.app.entity.enemy;

import com.app.cell.Cell;
import com.app.level.Direction;
import com.app.level.Level;

public class Ghost extends Enemy {

    /**
     * The number of hearts that each player initially has
     */
    protected static final int HEARTS = 1;

    protected static final int DAMAGE = -1;

    protected static final String SYMBOL = "👻";

    public Ghost(String name){
        super(name,HEARTS);
    }

    public Ghost(){
        this("Ghost" + (numberOfEnemies + 1));
    }
    
    public Direction getDirection(Level level){

        int playerLine = level.getPlayerLine();
        int playerColumn = level.getPlayerColumn();

        if(this.getCurrentColumn() > playerColumn){
            return Direction.LEFT;
        
        } else if(this.getCurrentColumn() < playerColumn){
            return Direction.RIGHT;

        } else {

            if(this.getCurrentLine() < playerLine){
                return Direction.DOWN;
            
            } else if(this.getCurrentLine() > playerLine){
                return Direction.UP;
            }

        }

        return Direction.NONE;
        
    }
    
    public boolean validMovement(Cell cell){
        return true;
    }

    public String getSymbol(){
        return SYMBOL;
    }

    public int getDamage(){
        return DAMAGE;
    }
}
