package com.app;

import java.io.IOException;
import java.nio.file.*;

enum Direction {
    NONE,
    UP,
    DOWN,
    LEFT,
    RIGHT
}

/**
 * The level class that contains the grid (map)
 * @version 2.0 (Second world)
 * @since 2.0
 * @author Rayane
 */
public class Level {
    private int length;
    private int width;
    private char[][] grid;
    private Player player;
    private Coordinates coordinates;

    /**
     * The level constructor
     * @param length The length of the grid
     * @param width The width of the grid
     * @param player The player that belongs to this level
     * @param coordinates The coordinates of the player
     */
    public Level(int length, int width, Player player, Coordinates coordinates){

        if(length <= 0 || width <= 0){
            throw new RuntimeException("The dimensions of the grid are invalid");
        }

        if(player == null){
            throw new RuntimeException("There is no existing player in the level");
        }

        if(coordinates.getLine() < 0 || coordinates.getLine() >= length || coordinates.getColumn() < 0 || coordinates.getColumn() >= width){
            throw new RuntimeException("The player is outside the bounds of the grid");
        }

        char[][] grid = new char[length][width];
        int i = 0, j = 0;

        //Creation of the grid
        for(i = 0; i < length; i++){
            for(j = 0; j < width; j++){
                if(i == 0 || i == length - 1 || j == 0 || j == width - 1){
                    grid[i][j] = '#';
                }
                else{
                    grid[i][j] = ' ';
                }
            }
        }

        if(grid[coordinates.getLine()][coordinates.getColumn()] == '#'){
            throw new RuntimeException("The player is placed on a wall");
        }

        this.length = length;
        this.width = width;
        this.grid = grid;
        this.player = player;
        this.coordinates = coordinates;
    }

    public Level(String fileName, Player player){

        Path path = Path.of(fileName);

        try{
            String content = Files.readString(path);

            if(!validContent(content)){
                throw new Exception("The file's content is not valid");
            }

            int i = 0, j = 0;
            int lines = 1;
            int maxLineLength = 0;
            int counter = 0;
        
            for(i = 0; i < content.length(); i++){
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

            int length = lines, width = maxLineLength;

            char[][] grid = new char[length][width];

            int c = 0;

            i=0;
            j=0;
            int k=0;

            int line = -1, column = -1;

            for(c = 0; c < content.length(); c++){
                if(content.charAt(c) == '\n'){
                    for(k = j; k < width; k++){
                        grid[i][k] = ' ';
                    }
                    i++;
                    j=0;
                }
                else{
                    if(content.charAt(c) == '1'){
                        line = i;
                        column = j;

                        grid[i][j] = ' ';
                    }
                    else{
                        grid[i][j] = content.charAt(c);
                    }
                    j++;
                }
            }

            this.length = lines;
            this.width = maxLineLength;
            this.grid = grid;
            this.player = player;
            this.coordinates = new Coordinates(line,column);
        }
        catch(IOException e){
            System.err.println("The file's content can't be accessed");
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }

    }

    public boolean validContent(String content){
        int i = 0;
        String valid = "1 #\n";
        int counter = 0;
        char car;

        for(i = 0; i < content.length(); i++){
            car = content.charAt(i);

            if(!valid.contains("" + car)){
                return false;
            }

            if(car == '1'){
                counter++;
            }
        }

        if(counter != 1){
            return false;
        }

        return true;
    }


    /**
     * Displays the grid of the level
     */
    @Override
    public String toString(){
        String string= "";

        int i = 0, j = 0;

        for(i = 0; i < this.length; i++){
            for(j = 0; j < this.width; j++){
                if(i == this.coordinates.getLine() && j == this.coordinates.getColumn()){
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

    public void move(Direction direction){

        int line = this.coordinates.getLine();
        int column = this.coordinates.getColumn();

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
}