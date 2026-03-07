package com.app.entity.enemy;

import java.util.ArrayList;
import java.util.List;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.cell.Coordinates;
import com.app.level.Direction;
import com.app.level.Level;

public class Hunter extends Enemy {

    /**
     * The number of hearts that each player initially has
     */
    protected static final int HEARTS = 3;

    protected static final int DAMAGE = -2;

    protected static final String SYMBOL = "🤖";

    public Hunter(String name){
        super(name,HEARTS);
    }

    public Hunter(){
        this("Hunter" + (numberOfEnemies + 1));
    }

    private List<Direction> getPath(Level level, Direction[][] edgeTo){
        List<Direction> path = new ArrayList<>();
        int line = level.getPlayerLine();
        int column = level.getPlayerColumn();
        Direction direction = edgeTo[line][column];

        do{
            path.addFirst(direction);

            switch(direction){

                case UP :
                    line++;
                    break;

                case DOWN :
                    line--;
                    break;

                case LEFT :
                    column++;
                    break;

                case RIGHT :
                    column--;
                    break;

                default :
                    break;

            }

            direction = edgeTo[line][column];

        } while(direction != Direction.NONE);

        return path;
    }
    
    public Direction getDirection(Level level){

        int playerLine = level.getPlayerLine();
        int playerColumn = level.getPlayerColumn();

        int lines = level.getHeight();
        int columns = level.getWidth();

        int hunterLine = this.getCurrentLine();
        int hunterColumn = this.getCurrentColumn();

        Direction[][] edgeTo = new Direction[lines][columns];

        List<Coordinates> queue = new ArrayList<>();
        queue.add(new Coordinates(hunterLine,hunterColumn));

        edgeTo[hunterLine][hunterColumn] = Direction.NONE;

        while(!queue.isEmpty()){
            Coordinates coordinates = queue.getFirst();
            queue.removeFirst();

            int line = coordinates.getLine();
            int column = coordinates.getColumn();

            if(edgeTo[line][column] != null){
                if(line > 0 && level.validCell(this,line - 1,column) && edgeTo[line - 1][column] == null){
                    edgeTo[line - 1][column] = Direction.UP;
                    queue.addLast(new Coordinates(line - 1,column));
                }

                if(line < lines - 1 && level.validCell(this,line + 1,column) && edgeTo[line + 1][column] == null){
                    edgeTo[line + 1][column] = Direction.DOWN;
                    queue.addLast(new Coordinates(line + 1,column));
                }

                if(column > 0 && level.validCell(this,line,column - 1) && edgeTo[line][column - 1] == null){
                    edgeTo[line][column - 1] = Direction.LEFT;
                    queue.addLast(new Coordinates(line,column - 1));
                }

                if(column < columns - 1 && level.validCell(this,line,column + 1) && edgeTo[line][column + 1] == null){
                    edgeTo[line][column + 1] = Direction.RIGHT;
                    queue.addLast(new Coordinates(line,column + 1));
                }
            }
        }

        if(edgeTo[playerLine][playerColumn] == null){
            return Direction.NONE;
        }

        List<Direction> path = this.getPath(level,edgeTo);

        return path.getFirst();
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