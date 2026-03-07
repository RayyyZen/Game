package com.app.level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.cell.Coordinates;
import com.app.entity.Player;
import com.app.entity.enemy.*;

abstract class LevelLoader {

    /**
     * Checks if a file's content is valid (must contain only these symbols : ' ', '#', '.', '*', 'D', 'R', 'G', 'C' and '\n' with a unique occurence of '1')
     * @param content The file's content
     * @return true if the file's content is valid, or false otherwise
     */
    private static boolean validContent(String content){
        String validSymbols = "1 #*.DRGC\n";
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

    static Level load(String fileName, Player player) throws IOException{

        if(player == null){
            throw new NullPointerException("There is no existing player in the level");
        }

        String content = Files.readString(Path.of(fileName));
        //This line can throw an IOException

        if(!validContent(content)){
            throw new IllegalArgumentException("The file's content is not valid, it must contain only these symbols : ' ', '#' and '\\n' with a unique occurence of '1'");
        }

        int lines = 1;
        //Counts the number of lines

        int maxLineLength = 0;
        //Gets the line with the most characters

        int counter = 0;
        //Counts the number of characters in a line
    
        for(int i = 0; i < content.length(); i++){
            if(content.charAt(i) == '\n'){

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

        for(int index = 0; index < content.length(); index++){

            char car = content.charAt(index);

            Coordinates coordinates = new Coordinates(line,column);

            Enemy enemy = null;

            switch(car){

                case '\n' :
                    for(int col = column; col < width; col++){
                        grid[line][col] = new Cell(new Coordinates(line,col), CellType.EMPTY, false);
                        //Complete the line with ' ' symbol
                    }

                    line++;
                    //Move to the next line of the grid
                    column = 0;
                    break;

                case '*' :
                    grid[line][column] = new Cell(coordinates, CellType.TRAP, false);
                    break;

                case '1' :
                    playerLine = line;
                    playerColumn = column;
                    //Get the player's coordinates
                    //If this code is reached it means that the file is valid so there is a unique player on the grid

                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, false);
                    break;

                case '.' :
                    numberOfCoins++;
                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, true);
                    break;

                case ' ' :
                    grid[line][column] = new Cell(coordinates, CellType.EMPTY, false);
                    break;

                case '#' :
                    grid[line][column] = new Cell(coordinates, CellType.WALL, false);
                    break;

                case 'D' :
                    grid[line][column] = new Cell(coordinates, CellType.LOCKED_DOOR, false);
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

            }

            if(enemy != null){
                enemy.setCoordinates(line,column);
                enemy.setSpawnCoordinates(line,column);
                enemies.add(enemy);

                Cell cell = new Cell(coordinates, CellType.EMPTY, false);
                grid[line][column] = cell;
                occupiedCells.add(cell);
            }

            if(car != '\n'){
                column++;
                //Move to the next column of the grid
            }

        }

        player.setCoordinates(playerLine,playerColumn);
        player.setSpawnCoordinates(playerLine,playerColumn);

        return new Level(grid,player,enemies,occupiedCells,numberOfCoins);
    }
}
