package com.app.cell;

/**
 * The cell class that contains its coordinates, its type and eventually a coin
 * @version 3.0 (Third world)
 * @since 3.0
 * @author Rayane
 */
public class Cell {
    
    /**
     * The coordinates of the cell
     */
    private final Coordinates coordinates;

    /**
     * The cell's type
     */
    private CellType type;

    /**
     * The variable that indicates if the cell contains a coin
     */
    private boolean containsCoin;

    /**
     * Checks if a cell's type is null
     * @param type The cell's type that will be checked
     */
    private void checkCellType(CellType type){
        if(type == null){
            throw new IllegalArgumentException("The cell's type can't be null");
        }
    }

    /**
     * The cell's constructor that takes as arguments the coordinates, the type and a variable indicating if it contains a coin
     * @param coordinates The coordinates of the cell
     * @param type The type of the cell
     * @param containsCoin The variable that indicates if the cell contains a coin
     */
    public Cell(Coordinates coordinates, CellType type, boolean containsCoin){
        if(coordinates == null || coordinates.getLine() < 0 || coordinates.getColumn() < 0){
            throw new IllegalArgumentException("The cell's coordinates are invalid");
        }

        checkCellType(type);

        this.coordinates = new Coordinates(coordinates.getLine(),coordinates.getColumn());
        //To avoid changing coordinates resulting in changing this.coordinates
        this.type = type;
        this.containsCoin = containsCoin;
    }

    /**
     * Returns the cell's type
     * @return The cell's type
     */
    public CellType getType(){
        return this.type;
    }

    /**
     * Returns the boolean that indicates if the cell contains a coin
     * @return true if the cell contains a coin or false otherwise
     */
    public boolean containsCoin(){
        return this.containsCoin;
    }

    /**
     * Modify the type of the cell
     * @param type The cell's new type
     */
    public void setType(CellType type){
        checkCellType(type);
        this.type = type;
    }

    /**
     * Removes the coin from the cell
     */
    public void removeCoin(){
        this.containsCoin = false;
    }

    /**
     * Returns a String that contains the cell's attributs
     * @return A String that contains the cell's attributs
     */
    @Override
    public String toString(){
        return "Cell :\n -> " + this.coordinates + "\n -> Type : " + this.type + "\n -> Contains coin : " + this.containsCoin;
    }

    /**
     * Checks if a cell is equal to an object
     * @param obj The object that will be compared to the cell
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Cell)){
            return false;
        }

        if(this == obj){
            return true;
        }

        Cell cell = (Cell) obj;

        return this.coordinates.equals(cell.coordinates);
    }

    @Override
    public int hashCode(){
        return this.coordinates.hashCode();
    }
}
