package com.app.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.entity.*;
import com.app.entity.enemy.Enemy;

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
    private Cell[][] grid;

    /**
     * The player that will play the level
     */
    private Player player;

    private List<Enemy> enemies;

    private Set<Cell> occupiedCells;

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

    Level(Cell[][] grid, Player player, List<Enemy> enemies, Set<Cell> occupiedCells, int numberOfCoins){
        this.grid = grid;
        this.player = player;
        this.enemies = enemies;
        this.occupiedCells = occupiedCells;
        this.numberOfCoins = numberOfCoins;
    }

    private Level(Level level){
        this(level.grid,level.player,level.enemies,level.occupiedCells,level.numberOfCoins);
    }

    /**
     * The level constructor that takes as arguments a filename and a unique player
     * @param fileName The filename that will be used to create the level's grid
     * @param player The player that will play the level
     * @throws IOException if the file doesn't exist or the file's content can't be accessed
     */
    public Level(String fileName, Player player) throws IOException{
        this(LevelLoader.load(fileName,player));
    }

    /**
     * Returns the number of coins the level contains
     * @return The number of coins the level contains
     */
    public int getNumberOfCoins(){
        return this.numberOfCoins;
    }

    public int getPlayerLine(){
        return this.player.getCurrentLine();
    }

    public int getPlayerColumn(){
        return this.player.getCurrentColumn();
    }

    public int getHeight(){
        return this.grid.length;
    }

    public int getWidth(){
        return this.grid[0].length;
    }

    private void move(Entity mobile, Direction direction){

        int mobileLine = mobile.getCurrentLine();
        int mobileColumn = mobile.getCurrentColumn();
        //Gets the player's coordinates

        int lines = this.grid.length;
        int columns = this.grid[mobileLine].length;

        switch(direction){

            case UP :
                mobileLine--;
                break;

            case DOWN :
                mobileLine++;
                break;

            case LEFT :
                mobileColumn--;
                break;

            case RIGHT :
                mobileColumn++;
                break;

            default :
                return;

        }

        int line = mobileLine;
        int column = mobileColumn;

        if(mobile instanceof Player){
            line = (mobileLine + lines) % lines;
            //The line range will be [0,lines-1] so it is on the grid
            column = (mobileColumn + columns) % columns;
            //The column range will be [0,columns-1] so it is on the grid
        }

        if(line >= 0 && line < lines && column >= 0 && column < columns && mobile.validMovement(this.grid[line][column])){
            mobile.setCoordinates(line,column);
        }
    }

    /**
     * Moves the player to a specific direction
     * @param direction The direction that will be taken by the player
     */
    public void movePlayer(Direction direction){
        move(this.player,direction);
    }

    public boolean validCell(Entity mobile, int line, int column){
        return mobile.validMovement(this.grid[line][column]);
    }

    private Cell getMobileCell(Entity mobile){
        return this.grid[mobile.getCurrentLine()][mobile.getCurrentColumn()];
    }

    private void moveEnemy(Enemy enemy){
        boolean remove = true;
        for(Enemy e : this.enemies){
            if(!e.equals(enemy) && e.getCurrentLine() == enemy.getCurrentLine() && e.getCurrentColumn() == enemy.getCurrentColumn()){
                remove = false;
            }
        }

        if(remove){
            this.occupiedCells.remove(this.getMobileCell(enemy));
        }
        
        move(enemy,enemy.getDirection(this));
        //this is the level
        this.occupiedCells.add(this.getMobileCell(enemy));
    }

    public void moveAllEnemies(){
        for(Enemy enemy : this.enemies){
            moveEnemy(enemy);
        }
    }

    /**
     * This function launches an action according to the object that is on the same cell as the player
     */
    public void effect(){
        int playerLine = this.player.getCurrentLine();
        int playerColumn = this.player.getCurrentColumn();
        //Get the player's coordinates

        Cell cell = this.grid[playerLine][playerColumn];
        //Get the cell that the player is located on

        if(cell.containsCoin()){
            this.player.modifyScore(COIN);
            cell.removeCoin();
            this.numberOfCoins--;
        }

        boolean bringToSpawn = false;

        if(cell.getType() == CellType.TRAP){
            this.player.modifyNumberOfHearts(TRAP);
            cell.setType(CellType.EMPTY);
            bringToSpawn = true;
        }

        if(this.occupiedCells.contains(this.getMobileCell(this.player))){
            for(Enemy enemy : this.enemies){
                if(enemy.getCurrentLine() == playerLine && enemy.getCurrentColumn() == playerColumn){
                    this.player.modifyNumberOfHearts(enemy.getDamage());
                    bringToSpawn = true;
                }
            }
        }

        if(bringToSpawn){
            this.player.setCoordinates(this.player.getSpawnLine(),this.player.getSpawnColumn());
            //Brings the player to the spawn

            for(Enemy enemy : this.enemies){
                this.occupiedCells.remove(this.getMobileCell(enemy));
                enemy.setCoordinates(enemy.getSpawnLine(),enemy.getSpawnColumn());
                this.occupiedCells.add(this.getMobileCell(enemy));
            }
        }

    }

    public List<String> getEnemiesAttributes(){
        List<String> names = new ArrayList<>();
        for(Enemy enemy : this.enemies){
            if(enemy.getCurrentLine() == this.player.getCurrentLine() && enemy.getCurrentColumn() == this.player.getCurrentColumn()){
                names.add(enemy.toString());
            }
        }
        return names;
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
                    string.append(this.player.getSymbol());
                    
                } else if(i == this.player.getSpawnLine() && j == this.player.getSpawnColumn()) {
                    string.append("🌀");

                } else if(this.occupiedCells.contains(this.grid[i][j])) {

                    for(Enemy enemy : this.enemies){
                        if(enemy.getCurrentLine() == i && enemy.getCurrentColumn() == j){
                            string.append(enemy.getSymbol());
                            break;
                        }
                    }

                } else {

                    switch(this.grid[i][j].getType()){
                        case WALL :
                            string.append("🔳");
                            break;

                        case TRAP :
                            // 🕸🧨⛓🔗💣⚓♨🧷🌫🕳💢
                            string.append("🔗");
                            break;

                        case LOCKED_DOOR :
                            //🚪🔓🔓🔑🗝
                            string.append("🔐");
                            break;

                        default : //EMPTY
                            if(this.grid[i][j].containsCoin()){
                                // 👛📀🟡
                                string.append("📀");
                            }
                            else{
                                string.append("  ");
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

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(this.grid);
    }
}