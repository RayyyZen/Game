package com.app.model.entity.enemy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.app.model.cell.Coordinates;
import com.app.model.level.Direction;
import com.app.model.level.Level;

/**
 * The hunter class that contains his attributes
 * @version 5.0 (Fifth world)
 * @since 4.0 (Fourth world)
 * @author Rayane
 */
public class Hunter extends Enemy {

    /**
     * The number of hearts that each hunter initially has
     */
    private static final int HEARTS = 3;

    /**
     * The symbol the represents the hunters
     */
    private static final String SYMBOL = "🤖";

    /**
     * The number of damage that each hunter can cause
     */
    private static final int DAMAGE = -2;

    /**
     * The hunter constructor that takes as an argument only a name
     * @param name The name of the hunter
     */
    public Hunter(String name){
        super(name,HEARTS,SYMBOL,DAMAGE);
    }

    /**
     * The hunter constructor that doesn't take any arguments and automatically generates his name
     */
    public Hunter(){
        this("Hunter" + (numberOfEnemies + 1));
    }

    /**
     * Finds the list of the directions starting from the player's locations that leads to the hunter's ones
     * @param level The level where the hunter is located
     * @param edgeTo The 2D array that contains the taken direction that leaded to each cell
     * @return A list of the directions that connects the player's and the hunter's both locations
     */
    private List<Direction> getPath(Level level, Direction[][] edgeTo){

        List<Direction> path = new ArrayList<>();
        int line = level.getPlayerLine();
        int column = level.getPlayerColumn();
        Direction direction = edgeTo[line][column];

        do{
            path.addFirst(direction);

            //We go back to the opposite direction in order to know the position we came from
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
    
    /**
     * Finds a valid direction where the hunter will move
     * The hunter takes the shortest path that is leading to the player
     * @param level The level that the hunter is located on
     * @return A valid direction where the hunter will move
     */
    public Direction getDirection(Level level){

        int playerLine = level.getPlayerLine();
        int playerColumn = level.getPlayerColumn();

        int lines = level.getHeight();
        int columns = level.getWidth();

        int hunterLine = this.getCurrentLine();
        int hunterColumn = this.getCurrentColumn();

        Direction[][] edgeTo = new Direction[lines][columns];

        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(new Coordinates(hunterLine,hunterColumn));

        edgeTo[hunterLine][hunterColumn] = Direction.NONE;

        while(!queue.isEmpty()){
            Coordinates coordinates = queue.poll();

            int line = coordinates.getLine();
            int column = coordinates.getColumn();

            if(edgeTo[line][column] != null){
                if(line > 0 && level.validCell(this,line - 1,column) && edgeTo[line - 1][column] == null){
                    edgeTo[line - 1][column] = Direction.UP;
                    queue.add(new Coordinates(line - 1,column));
                }

                if(line < lines - 1 && level.validCell(this,line + 1,column) && edgeTo[line + 1][column] == null){
                    edgeTo[line + 1][column] = Direction.DOWN;
                    queue.add(new Coordinates(line + 1,column));
                }

                if(column > 0 && level.validCell(this,line,column - 1) && edgeTo[line][column - 1] == null){
                    edgeTo[line][column - 1] = Direction.LEFT;
                    queue.add(new Coordinates(line,column - 1));
                }

                if(column < columns - 1 && level.validCell(this,line,column + 1) && edgeTo[line][column + 1] == null){
                    edgeTo[line][column + 1] = Direction.RIGHT;
                    queue.add(new Coordinates(line,column + 1));
                }
            }
        }

        if(edgeTo[playerLine][playerColumn] == null){//It means that there isn't any paths that leads to the player
            return Direction.NONE;
        }

        List<Direction> path = this.getPath(level,edgeTo);

        return path.getFirst();
    }
}