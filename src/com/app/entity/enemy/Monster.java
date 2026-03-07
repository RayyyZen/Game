package com.app.entity.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.level.Direction;
import com.app.level.Level;

public class Monster extends Enemy {

    /**
     * The number of hearts that each player initially has
     */
    protected static final int HEARTS = 1;

    protected static final int DAMAGE = -1;

    protected static final String SYMBOL = "👾";

    public Monster(String name){
        super(name,HEARTS);
    }

    public Monster(){
        this("Monster" + (numberOfEnemies + 1));
    }

    public Direction getDirection(Level level){

        List<Direction> directions = new ArrayList<>();

        int enemyLine = this.getCurrentLine();
        int enemyColumn = this.getCurrentColumn(); 

        int lines = level.getHeight();
        int columns = level.getWidth();

        if(enemyLine > 0 && level.validCell(this,enemyLine - 1,enemyColumn)){
            directions.add(Direction.UP);
        }

        if(enemyLine < lines - 1 && level.validCell(this,enemyLine + 1,enemyColumn)){
            directions.add(Direction.DOWN);
        }

        if(enemyColumn > 0 && level.validCell(this,enemyLine,enemyColumn - 1)){
            directions.add(Direction.LEFT);
        }

        if(enemyColumn < columns - 1 && level.validCell(this,enemyLine,enemyColumn + 1)){
            directions.add(Direction.RIGHT);
        }

        if(directions.isEmpty()){
            return Direction.NONE;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0,directions.size());
        return directions.get(randomIndex);

    }
    
    public boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY;
    }

    public String getSymbol(){
        return SYMBOL;
    }

    public int getDamage(){
        return DAMAGE;
    }
}
