package com.app;

import java.io.IOException;
import java.nio.file.*;

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
 * @version 2.0 (Second world)
 * @since 2.0
 * @author Rayane
 */
public class Level {

    /**
     * The length of the level's grid
     */
    private int length;

    /**
     * The width of the level's grid
     */
    private int width;

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
    public boolean validContent(String content){
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

        if(counter != 1){
            //There isn't a unique player on the level's grid
            return false;
        }

        return true;
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
            throw new IllegalArgumentException("The file's content is not valid, it must containt only those symbols : ' ', '#' and '\n' with a unique occurence of '1'");
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
            }
            else{
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
            }
            else{
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

        this.length = lines;
        this.width = maxLineLength;
        this.grid = grid;
        this.player = player;
        this.coordinates = new Coordinates(playerLine,playerColumn);
    }

    /**
     * Moves the player to a specific direction
     * @param direction The direction that will be taken by the player
     */
    public void move(Direction direction){

        int line = this.coordinates.getLine();
        int column = this.coordinates.getColumn();
        //Gets the player's coordinates

        switch(direction){

            case UP :
                if(line > 0 && this.grid[line - 1][column] != '#'){
                    this.coordinates.setLine(line - 1);
                }
                break;

            case DOWN :
                if(line < this.length - 1 && this.grid[line + 1][column] != '#'){
                    this.coordinates.setLine(line + 1);
                }
                break;

            case LEFT :
                if(column > 0 && this.grid[line][column - 1] != '#'){
                    this.coordinates.setColumn(column - 1);
                }
                break;

            case RIGHT :
                if(column < this.width - 1 && this.grid[line][column + 1] != '#'){
                    this.coordinates.setColumn(column + 1);
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
        String string= "";

        for(int i = 0; i < this.length; i++){
            for(int j = 0; j < this.width; j++){
                if(i == this.coordinates.getLine() && j == this.coordinates.getColumn()){
                    //If this code is reached it means that this position is the player's one
                    string += "1";
                }
                else{
                    string += grid[i][j];
                }
            }
            string += "\n";
        }

        return string;
    }
}