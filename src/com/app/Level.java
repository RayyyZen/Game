package com.app;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

/**
 * The enumeration that contains the direction the player wants to take
 */
enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE
}

/**
 * The level class that contains the grid (map) and a unique player who will play on it
 * @version 2.1 (Second world)
 * @since 2.0
 * @author Rayane
 */
public class Level {

    /**
     * The grid that contains the level's map
     */
    private char[][] grid;

    /**
     * The player that will play the level
     */
    private Player player;

    /**
     * The coordinates of the player on the level's grid
     */
    private Coordinates coordinates;

    /**
     * Checks if a file's content is valid (must contain only these symbols : ' ', '#' and '\n' with a unique occurence of '1')
     * @param content The file's content
     * @return true if the file's content is valid, or false otherwise
     */
    private boolean validContent(String content){
        String validSymbols = "1 #\n";
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
     * The level constructor that takes as arguments a filename and a unique player
     * @param fileName The filename that will be used to create the level's grid
     * @param player The player that will play the level
     * @throws IOException if the file doesn't exist or the file's content can't be accessed
     */
    public Level(String fileName, Player player) throws IOException{

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
        char[][] grid = new char[length][width];

        for(int index = 0; index < content.length(); index++){
            char car = content.charAt(index);

            if(car == '\n'){
                for(int col = column; col < width; col++){
                    grid[line][col] = ' ';
                    //Complete the line with ' ' symbol
                }

                line++;
                //Move to the next line of the grid
                column = 0;

            } else {
                char tmpCar = car;

                if(car == '1'){
                    playerLine = line;
                    playerColumn = column;
                    //Get the player's coordinates
                    //If this code is reached it means that the file is valid so there is a unique player on the grid

                    tmpCar = ' ';
                }

                grid[line][column] = tmpCar;
                column++;
                //Move to the next column of the grid
            }
        }

        this.grid = grid;
        this.player = player;
        this.coordinates = new Coordinates(playerLine,playerColumn);
    }

    /**
     * Moves the player to a specific direction
     * @param direction The direction that will be taken by the player
     */
    public void move(Direction direction){

        int playerLine = this.coordinates.getLine();
        int playerColumn = this.coordinates.getColumn();
        //Gets the player's coordinates

        int lines = this.grid.length;
        int columns = this.grid[playerLine].length;

        switch(direction){

            case UP :
                if(playerLine > 0 && this.grid[playerLine - 1][playerColumn] != '#'){
                    this.coordinates.setLine(playerLine - 1);
                }
                break;

            case DOWN :
                if(playerLine < lines - 1 && this.grid[playerLine + 1][playerColumn] != '#'){
                    this.coordinates.setLine(playerLine + 1);
                }
                break;

            case LEFT :
                if(playerColumn > 0 && this.grid[playerLine][playerColumn - 1] != '#'){
                    this.coordinates.setColumn(playerColumn - 1);
                }
                break;

            case RIGHT :
                if(playerColumn < columns - 1 && this.grid[playerLine][playerColumn + 1] != '#'){
                    this.coordinates.setColumn(playerColumn + 1);
                }
                break;
        }

    }

    /**
     * Returns a String that contains level's grid with the player on it
     * @return A String that contains level's structure and the player on it
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        for(int i = 0; i < this.grid.length; i++){
            for(int j = 0; j < this.grid[i].length; j++){
                if(i == this.coordinates.getLine() && j == this.coordinates.getColumn()){
                    //If this code is reached it means that this position is the player's one
                    string.append("1");
                    
                } else {
                    string.append(grid[i][j]);
                }
            }
            string.append("\n");
        }

        return string.toString();
    }

    /**
     * Checks if a level is equal to an object
     * @param obj The object that will be compared to the level
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Level)){
            return false;

        } else if(this == obj) {
            return true;
        }

        Level level = (Level)obj;

        if(this.grid.length != level.grid.length || !Objects.equals(this.player,level.player) || !Objects.equals(this.coordinates,level.coordinates)){
            //Use of Objects.equals(a,b) instead of a != null && a.equals(b)
            return false;
        }
        //If we are here it means this.grid.length == level.grid.length

        for(int i = 0; i < this.grid.length; i++){
            if(this.grid[i].length != level.grid[i].length){
                return false;
            }
            //If we are here it means this.grid[i].length == level.grid[i].length

            for(int j = 0; j < this.grid[i].length; j++){
                if(this.grid[i][j] != level.grid[i][j]){
                    return false;
                }
            }
        }

        return true;
    }
}