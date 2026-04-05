package com.app.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.app.cell.Cell;
import com.app.cell.CellType;
import com.app.entity.*;
import com.app.entity.enemy.Enemy;
import com.app.usable.Usable;

/**
 * The level class that contains the grid (map), a unique player who plays on it and the enemies
 * @version 5.0 (Fifth world)
 * @since 2.0 (Second world)
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

    /**
     * The enemies that are located on the level
     */
    private List<Enemy> enemies;

    /**
     * The cells that are occupied by at least one enemy
     */
    private Set<Cell> occupiedCells;

    /**
     * The number of coins that the level has
     * We use this variable to see how many coins are left in the level to avoid iterating through the level's grid each time
     */
    private int numberOfCoins;

    private int blockEnemies;

    private WinCondition winCondition;

    /**
     * Number of hearts that the player loses from falling into a trap
     */
    private static final int TRAP = -2;

    /**
     * The level's constructor used by the level's loader in order to return a created level
     * This level's constructor is package private in order to be called in the LevelLoader class
     * @param grid The grid that contains the level's map
     * @param player The player who is located on the level
     * @param enemies The enemies located on the level
     * @param occupiedCells The cells that are occupied by at least one enemy on the level
     * @param numberOfCoins The number of coins that the level has
     */
    Level(Cell[][] grid, Player player, List<Enemy> enemies, Set<Cell> occupiedCells, int numberOfCoins, int blockEnemies, WinCondition winCondition){
        this.grid = grid;
        this.player = player;
        this.enemies = enemies;
        this.occupiedCells = occupiedCells;
        this.numberOfCoins = numberOfCoins;
        this.blockEnemies = blockEnemies;
        this.winCondition = winCondition;
    }

    /**
     * The level's constructor that copies the attributes of a level given as an argument
     * @param level The level that will be used to create a new one by copying its attributes
     */
    private Level(Level level){
        this(level.grid,level.player,level.enemies,level.occupiedCells,level.numberOfCoins,level.blockEnemies,level.winCondition);
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

    /**
     * Returns the current coordinates line of the player on the level's grid
     * @return The current coordinates line of the player on the level's grid
     */
    public int getPlayerLine(){
        return this.player.getCurrentLine();
    }

    /**
     * Returns the current coordinates column of the player on the level's grid
     * @return The current coordinates column of the player on the level's grid
     */
    public int getPlayerColumn(){
        return this.player.getCurrentColumn();
    }

    /**
     * Returns the heigth of the level's grid
     * @return the heigth of the level's grid
     */
    public int getHeight(){
        return this.grid.length;
    }

    /**
     * Returns the width of the level's grid
     * @return the width of the level's grid
     */
    public int getWidth(){
        return this.grid[0].length;
    }

    public void blockEnemies(int numberOfMovements){
        if(numberOfMovements < 0){
            throw new IllegalArgumentException("The enemies can't be blocked for a negative number of movements !");
        }

        this.blockEnemies += numberOfMovements;
    }

    /**
     * Removes a coin from the level
     */
    public void removeCoin(){
        if(this.numberOfCoins > 0){
            this.numberOfCoins--;
        }
    }

    public void modifyPlayerScore(int score){
        this.player.modifyScore(score);
    }

    /**
     * Moves a box on the level
     * @param line The line of the box that will be moved
     * @param column The column of the box that will be moved
     * @param paddingLine The increasing line's incrementing to check if another box is related to the main one
     * @param paddingColumn The increasing column's incrementing to check if another box is related to the main one
     * @return True if the box was able to move, or false otherwise
     */
    private boolean moveBox(int line, int column, int paddingLine, int paddingColumn){

        int lines = this.getHeight();
        int columns = this.getWidth();

        int l = (line + paddingLine + lines) % lines, c = (column + paddingColumn + columns) % columns;

        while(this.grid[l][c].containsBox()){//Reaches the coordinates where a box should appear
            l = (l + paddingLine + lines) % lines;
            c = (c + paddingColumn + columns) % columns;
        }

        if((this.grid[l][c].getType() == CellType.EMPTY || this.grid[l][c].getType() == CellType.HOLE) && !(this.player.getSpawnLine() == l && this.player.getSpawnColumn() == c) && !this.occupiedCells.contains(this.grid[l][c])){
            if(this.grid[l][c].getType() == CellType.HOLE){
                this.grid[l][c].setType(CellType.EMPTY);

            } else {
                this.grid[l][c].controlBox(true);
            }
            this.grid[line][column].controlBox(false);

            return true;
        }

        return false;
    }

    /**
     * Moves an entity on the level's grid
     * Moves also the boxes on a level's grid in case they were pushed by the player
     * @param entity The entity that will be moved
     * @param direction The direction that will be taken by the entity
     */
    private void move(Entity entity, Direction direction){

        int entityLine = entity.getCurrentLine();
        int entityColumn = entity.getCurrentColumn();
        //Gets the eneity's coordinates

        int lines = this.grid.length;
        int columns = this.grid[entityLine].length;

        int line = entityLine;
        int column = entityColumn;

        switch(direction){

            case UP :
                line--;
                break;

            case DOWN :
                line++;
                break;

            case LEFT :
                column--;
                break;

            case RIGHT :
                column++;
                break;

            default :
                return;

        }

        if(entity instanceof Player){
            line = (line + lines) % lines;
            //The line range will be [0,lines-1] so it is on the grid
            column = (column + columns) % columns;
            //The column range will be [0,columns-1] so it is on the grid

            if(this.grid[line][column].containsBox()){
                int paddingLine = line - entityLine, paddingColumn = column - entityColumn;
                if(!moveBox(line,column,paddingLine,paddingColumn)){//It means the box couldn't be moved
                    return ;
                }
            }
        }

        if(line >= 0 && line < lines && column >= 0 && column < columns && entity.validMovement(this.grid[line][column])){
            entity.setCoordinates(line,column);
        }
    }

    /**
     * Moves the player to a specific direction
     * @param direction The direction that will be taken by the player
     */
    public void movePlayer(Direction direction){
        move(this.player,direction);
    }

    /**
     * Checks if a cell is valid for a specific entity
     * @param entity The entity that wants to move on the cell
     * @param line The line on the level's grid of the entity
     * @param column The column on the level's grid of the entity
     * @return True if the cell is valid for the entity, or false otherwise
     */
    public boolean validCell(Entity entity, int line, int column){
        return entity.validMovement(this.grid[line][column]);
    }

    /**
     * Returns the cell occupied by an entity
     * @param entity The entity that is located on a cell
     * @return The cell where the entity is located on
     */
    private Cell getEntityCell(Entity entity){
        return this.grid[entity.getCurrentLine()][entity.getCurrentColumn()];
    }

    /**
     * Moves an enemy on the level's grid
     * @param enemy The enemy that will be moved
     */
    private void moveEnemy(Enemy enemy){
        boolean remove = true;

        for(Enemy e : this.enemies){
            if(!e.equals(enemy) && e.sameCoordinates(enemy)){
                remove = false;
            }
        }

        if(remove){//The cell is removed from the occupiedCells collection only if there isn't another enemy on it
            this.occupiedCells.remove(this.getEntityCell(enemy));
        }
        
        move(enemy,enemy.getDirection(this));
        //this is the level
        this.occupiedCells.add(this.getEntityCell(enemy));
    }

    /**
     * Moves all the enemies of the level's grid
     */
    public void moveAllEnemies(){
        if(this.blockEnemies > 0){
            this.blockEnemies--;
            return ;
        }

        for(Enemy enemy : this.enemies){
            moveEnemy(enemy);
        }
    }

    /**
     * Returns all the enemies attributes that hitted the player
     * @return all the enemies attributes that hitted the player
     */
    public List<String> getCollidedEnemies(){
        List<String> names = new ArrayList<>();

        for(Enemy enemy : this.enemies){
            if(enemy.sameCoordinates(this.player)){
                names.add(enemy.toString());
            }
        }

        return names;
    }

    /**
     * This function launches an action according to the cell where the player is located
     */
    public void effect(){
        int playerLine = this.player.getCurrentLine();
        int playerColumn = this.player.getCurrentColumn();
        //Get the player's coordinates

        Cell cell = this.grid[playerLine][playerColumn];
        //Get the cell that the player is located on

        boolean bringToSpawn = false;

        cell.pickUp(this);

        this.player.activateAutomaticUsables(this);

        if(cell.getType() == CellType.TRAP){
            this.player.modifyNumberOfHearts(TRAP);
            cell.setType(CellType.EMPTY);
            bringToSpawn = true;
        }

        if(this.occupiedCells.contains(this.getEntityCell(this.player))){
            for(Enemy enemy : this.enemies){
                if(enemy.sameCoordinates(player)){
                    this.player.modifyNumberOfHearts(enemy.getDamage());
                    bringToSpawn = true;
                }
            }
        }

        if(bringToSpawn){
            this.player.setCoordinates(this.player.getSpawnLine(),this.player.getSpawnColumn());
            //Brings the player to the spawn

            this.occupiedCells.clear();
            //Withdraw all the previous occupied cells

            for(Enemy enemy : this.enemies){
                enemy.setCoordinates(enemy.getSpawnLine(),enemy.getSpawnColumn());
                this.occupiedCells.add(this.getEntityCell(enemy));
            }
        }

        this.player.learnNewSkills(this);

    }

    public boolean addUsableToPlayer(Usable usable){
        return this.player.addUsable(usable);
    }

    /**
     * Checks if the player still has some hearts
     * @return true if the player doesn't have any hearts, or else otherwise
     */
    public boolean gameOver(){
        return this.player.getNumberOfHearts() <= 0;
    }

    public boolean win(){
        return this.winCondition.win(this);
    }

     /**
     * Returns a String that contains the level's grid with the player on it
     * @return A String that contains the level's structure and the player on it
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
                    string.append(this.grid[i][j].getSymbol());
                }
                
            }
            string.append("\n");
        }

        string.append("\n" + this.winCondition.label() + "\n");

        if(this.blockEnemies > 0){
            String s = this.blockEnemies > 1 ? "s" : "";
            string.append("The enemies are frozen for " + this.blockEnemies + " more movement" + s + "\n");
        }

        return string.toString() + "\n" + this.player.toString() + "\n";
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

    /**
     * Returns the hash code of the level
     * @return the hash code of the level
     */
    @Override
    public int hashCode(){
        return Arrays.deepHashCode(this.grid);
    }

    public boolean isEmptyCell(int line, int column){
        if(line < 0 || line >= this.getHeight() || column < 0 || column >= this.getWidth()){
            throw new ArrayIndexOutOfBoundsException("You can't check if a cell out of the grid bounds is empty !");
        }

        return this.grid[line][column].getType() == CellType.EMPTY && !this.grid[line][column].containsBox();
    }


    public void use(int inventoryIndex){
        this.player.use(inventoryIndex,this);
    }

    public void movePlayer(int line, int column){
        this.player.setCoordinates(line,column);
    }

    public boolean playerEnemyCollision(){
        Cell playerCell = this.grid[this.player.getCurrentLine()][this.player.getCurrentColumn()];
        return this.occupiedCells.contains(playerCell);
    }

    public void damageEnemiesOnCollision(int damage){
        int line = this.player.getCurrentLine();
        int column = this.player.getCurrentColumn();

        Cell playerCell = this.grid[line][column];

        if(!this.occupiedCells.contains(playerCell)){
            return ;
        }
        
        this.occupiedCells.clear();
        //Withdraw all the previous occupied cells

        Iterator<Enemy> iterator = this.enemies.iterator();

        while(iterator.hasNext()){
            Enemy enemy = iterator.next();

            if(enemy.sameCoordinates(this.player)){
                enemy.modifyNumberOfHearts(damage);

                if(enemy.getNumberOfHearts() <= 0){
                    iterator.remove();
                    this.player.incrementNumberOfKills();
                    continue;
                }

                enemy.setCoordinates(enemy.getSpawnLine(),enemy.getSpawnColumn());
            }
            this.occupiedCells.add(this.getEntityCell(enemy));
        }

    }

    public void playerCanLockpick(){
        this.player.canLockpick();
    }

    public int getNumberOfEnemies(){
        return this.enemies.size();
    }
}