package com.app.entity.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.level.Direction;
import com.app.level.Level;

/**
 * The monster class that contains his attributes
 * @version 4.0 (Fourth world)
 * @since 4.0 (Fourth world)
 * @author Rayane
 */
public class Monster extends Enemy {

    /**
     * The number of hearts that each monster initially has
     */
    private static final int HEARTS = 1;

    /**
     * The symbol the represents the monsters
     */
    private static final String SYMBOL = "👾";

    /**
     * The number of damage that each monster can cause
     */
    private static final int DAMAGE = -1;

    /**
     * The monster constructor that takes as an argument only a name
     * @param name The name of the monster
     */
    public Monster(String name){
        super(name,HEARTS,SYMBOL,DAMAGE);
    }

    /**
     * The monster constructor that doesn't take any arguments and automatically generates his name
     */
    public Monster(){
        this("Monster" + (numberOfEnemies + 1));
    }

    /**
     * Finds a valid direction where the monster will move
     * @param level The level that the monster is located on
     * @return A valid direction where the monster will move
     */
    public Direction getDirection(Level level){

        List<Direction> directions = new ArrayList<>();
        //The list that will contain all the possible directions the monster can take

        int monsterLine = this.getCurrentLine();
        int monsterColumn = this.getCurrentColumn(); 

        int lines = level.getHeight();
        int columns = level.getWidth();

        if(monsterLine > 0 && level.validCell(this,monsterLine - 1,monsterColumn)){
            directions.add(Direction.UP);
        }

        if(monsterLine < lines - 1 && level.validCell(this,monsterLine + 1,monsterColumn)){
            directions.add(Direction.DOWN);
        }

        if(monsterColumn > 0 && level.validCell(this,monsterLine,monsterColumn - 1)){
            directions.add(Direction.LEFT);
        }

        if(monsterColumn < columns - 1 && level.validCell(this,monsterLine,monsterColumn + 1)){
            directions.add(Direction.RIGHT);
        }

        if(directions.isEmpty()){
            return Direction.NONE;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0,directions.size());
        return directions.get(randomIndex);
    }
    
    /**
     * Checks if a cell is valid according to the monster's possible movements
     * The monsters can't walk on traps
     * @param cell The cell that will be checked
     * @return true if the cell is valid for the monster, or false otherwise
     */
    public boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY && !cell.containsBox();
    }
}