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
 * @version 3.0 (Third world)
 * @since 2.0
 * @author Rayane
 */
public class Level {

    /**
     * The grid that contains the level's map
     */
    public Cell[][] grid;

    /**
     * The player that will play the level
     */
    private Player player;

    /**
     * The number of coins that the level has
     * We use this variable to see how many coins are left in the level to avoid iterating through the level's grid each time
     */
    private int numberOfCoins;

    /**
     * Number of points (score) that the player's gain from a coin
     */
    private static final int COIN = 10;

    /**
     * Number of hearts that the player looses from falling into a trap
     */
    private static final int TRAP = -2;

    /**
     * Checks if a file's content is valid (must contain only these symbols : ' ', '#', '.', '*', 'D' and '\n' with a unique occurence of '1')
     * @param content The file's content
     * @return true if the file's content is valid, or false otherwise
     */
    private boolean validContent(String content){
        String validSymbols = "1 #*.D\n";
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
        int numberOfCoins = 0;
        Cell[][] grid = new Cell[length][width];

        for(int index = 0; index < content.length(); index++){

            char car = content.charAt(index);

            Coordinates coordinates = new Coordinates(line,column);

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

            }

            if(car != '\n'){
                column++;
                //Move to the next column of the grid
            }

        }

        this.grid = grid;
        this.player = player;
        this.player.setCoordinates(playerLine,playerColumn);
        this.player.setSpawnCoordinates(playerLine,playerColumn);
        this.numberOfCoins = numberOfCoins;
    }

    /**
     * Returns the number of coins the level contains
     * @return The number of coins the level contains
     */
    public int getNumberOfCoins(){
        return this.numberOfCoins;
    }

    /**
     * Checks if the player can move on a specific cell
     * @param cell A specific cell from the grid
     * @return true if the player can move on the cell, or false otherwise
     */
    private boolean validMovement(Cell cell){
        CellType type = cell.getType();
        return type == CellType.EMPTY || type == CellType.TRAP;
    }

    /**
     * Moves the player to a specific direction
     * @param direction The direction that will be taken by the player
     */
    public void move(Direction direction){

        int playerLine = this.player.getCurrentLine();
        int playerColumn = this.player.getCurrentColumn();
        //Gets the player's coordinates

        int lines = this.grid.length;
        int columns = this.grid[playerLine].length;

        switch(direction){

            case UP :
                playerLine--;
                break;

            case DOWN :
                playerLine++;
                break;

            case LEFT :
                playerColumn--;
                break;

            case RIGHT :
                playerColumn++;
                break;

            default :
                return;

        }

        int line = (playerLine + lines) % lines;
        //The line range will be [0,lines-1] so it is on the grid
        int column = (playerColumn + columns) % columns;
        //The column range will be [0,columns-1] so it is on the grid

        if(this.validMovement(this.grid[line][column])){
            this.player.setCoordinates(line,column);
        }

    }

    /**
     * This function launches an action according to the object that is on the same cell as the player
     */
    public void effect(){
        int playerLine = this.player.getCurrentLine(), playerColumn = this.player.getCurrentColumn();
        //Get the player's coordinates

        Cell cell = this.grid[playerLine][playerColumn];
        //Get the cell that the player is located on

        if(cell.getType() == CellType.TRAP){
            this.player.modifyNumberOfHearts(TRAP);
            cell.setType(CellType.EMPTY);;

            this.player.setCoordinates(this.player.getSpawnLine(),this.player.getSpawnColumn());
            //Brings the player to the spawn
        }

        if(cell.containsCoin()){
            this.player.modifyScore(COIN);
            cell.removeCoin();
            this.numberOfCoins--;
        }

    }

    /**
     * Checks if the player still has some hearts
     * @return true if the player doesn't have any hearts, or else otherwise
     */
    public boolean gameOver(){
        return this.player.getNumberOfHearts() <= 0;
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
                if(i == this.player.getCurrentLine() && j == this.player.getCurrentColumn()){
                    //If this code is reached it means that this position is the player's one
                    string.append("1");
                    
                } else if(i == this.player.getSpawnLine() && j == this.player.getSpawnColumn()) {
                    string.append("S");

                } else {

                    switch(this.grid[i][j].getType()){
                        case WALL :
                            string.append("#");
                            break;

                        case TRAP :
                            string.append("*");
                            break;

                        case LOCKED_DOOR :
                            string.append("D");
                            break;

                        default : //EMPTY
                            if(this.grid[i][j].containsCoin()){
                                string.append(".");
                            }
                            else{
                                string.append(" ");
                            }
                    }

                }
            }
            string.append("\n");
        }

        return string.toString() + "\n" + this.player + "\n";
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

        if(this.grid.length != level.grid.length){
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
                if(!this.grid[i][j].equals(level.grid[i][j])){
                    return false;
                }
            }
        }

        return true;
    }
}