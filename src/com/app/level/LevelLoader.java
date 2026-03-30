package com.app.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.app.cell.*;
import com.app.entity.*;
import com.app.entity.enemy.*;

/**
 * The level loading class that loads a level from a valid file
 * @version 4.0 (Fourth world)
 * @since 4.0 (Fourth world)
 * @author Rayane
 */
abstract class LevelLoader {

    /**
     * Checks if a file's content is valid (must contain only these symbols : ' ', '#', '.', '*', 'D', 'R', 'G', 'C', 'B', 'H' and '\n' with a unique occurence of '1')
     * @param content The file's content
     * @return true if the file's content is valid, or false otherwise
     */
    private static boolean validContent(String content){
        String validSymbols = "1 #*.DRGCBH\n";
        //All the valid symbols that are allowed in the file

        int counter = 0;
        //Counts the occurence of '1' in the level's grid

        for(int index = 0; index < content.length(); index++){
            char car = content.charAt(index);

            if(!validSymbols.contains("" + car)){
                return false;
            }

            if(car == '1'){
                counter++;
            }
        }

        return counter == 1;
        //There is a unique player on the level's grid
    }

    /**
     * Completes a level's line with empty cells
     * @param grid The level's grid
     * @param line The line that will be completed
     * @param column The first column that will be completed on the line
     * @param width The width of the level's grid
     */
    private static void completeLine(Cell[][] grid, int line, int column, int width){
        for(int col = column; col < width; col++){
            grid[line][col] = new Cell(new Coordinates(line,col), CellType.EMPTY, false, false);
            //Complete the line with empty cells
        }
    }

    /**
     * Loads a level from a file
     * @param fileName The filename that will be used to create the level's grid
     * @param player The player that will play the level
     * @return The created level
     * @throws IOException if the file doesn't exist or the file's content can't be accessed
     */
    static Level load(String fileName, Player player) throws IOException{

        if(player == null){
            throw new NullPointerException("There is no existing player in the level");
        }

        String content = Files.readString(Path.of(fileName));
        //This line can throw an IOException

        int contentLength = content.length();

        if(!validContent(content)){
            throw new IllegalArgumentException("The file's content is not valid, it must contain only these symbols : ' ', '#' and '\\n' with a unique occurence of '1'");
        }

        int lines = 1;
        //Counts the number of lines

        int maxLineLength = 0;
        //Gets the line with the most characters

        int counter = 0;
        //Counts the number of characters in a line
    
        for(int index = 0; index < contentLength; index++){
            if(content.charAt(index) == '\n'){

                if(maxLineLength < counter){
                    maxLineLength = counter;
                }
                
                lines++;
                counter = 0;

            } else {
                counter++;
            }
        }

        int line = 0, column = 0;
        int playerLine = -1, playerColumn = -1;
        int length = lines, width = maxLineLength;
        int numberOfCoins = 0;
        Cell[][] grid = new Cell[length][width];
        List<Enemy> enemies = new ArrayList<>();
        Set<Cell> occupiedCells = new HashSet<>();

        for(int index = 0; index < contentLength; index++){

            char car = content.charAt(index);

            Coordinates coordinates = new Coordinates(line,column);

            Enemy enemy = null;

            switch(car){

                case '\n' :
                    completeLine(grid,line,column,width);
                    line++;
                    //Move to the next line of the grid
                    column = 0;
                    break;

                case '*' :
                    grid[line][column] = new Cell(coordinates, CellType.TRAP, false, false);
                    break;

                case '1' :
                    playerLine = line;
                    playerColumn = column;
                    //Get the player's coordinates
                    //If this code is reached it means that the file is valid so there is a unique player on the grid

                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, false, false);
                    break;

                case '.' :
                    numberOfCoins++;
                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, true, false);
                    break;

                case ' ' :
                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, false, false);
                    break;

                case '#' :
                    grid[line][column] = new Cell(coordinates, CellType.WALL, false, false);
                    break;

                case 'D' :
                    grid[line][column] = new Cell(coordinates, CellType.LOCKED_DOOR, false, false);
                    break;

                case 'R' :
                    enemy = new Monster();
                    break;

                case 'G' :
                    enemy = new Ghost();
                    break;

                case 'C' :
                    enemy = new Hunter();
                    break;

                case 'B' :
                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, false, true);
                    break;

                case 'H' :
                    grid[line][column] = new Cell(coordinates, CellType.HOLE, false, false);
                    break;

            }

            if(enemy != null){//If an enemy was created
                enemy.setCoordinates(line,column);
                enemy.setSpawnCoordinates(line,column);
                enemies.add(enemy);

                Cell cell = new Cell(coordinates, CellType.EMPTY, false, false);
                grid[line][column] = cell;
                occupiedCells.add(cell);
            }

            if(car != '\n'){
                column++;
                //Move to the next column of the grid
            }

        }

        completeLine(grid,line,column,width);

        player.setCoordinates(playerLine,playerColumn);
        player.setSpawnCoordinates(playerLine,playerColumn);

        return new Level(grid,player,enemies,occupiedCells,numberOfCoins);
    }
}