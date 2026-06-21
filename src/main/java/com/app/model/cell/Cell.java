package com.app.model.cell;

import com.app.model.level.Level;
import com.app.model.usable.item.Item;
import com.app.model.usable.item.consumable.Consumable;
import com.app.model.usable.item.equipable.Equipable;

/**
 * The cell class that contains its coordinates, its type and eventually a box and an item
 * @version 5.0 (Fifth world)
 * @since 3.0 (Third world)
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
     * The variable that indicates if the cell contains a box
     */
    private boolean containsBox;

    /**
     * The item that is possibly located on the cell
     */
    private Item item;

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
     * The cell's constructor that takes as arguments the coordinates, the type, a variable indicating if it contains a box and eventually an item
     * @param coordinates The coordinates of the cell
     * @param type The type of the cell
     * @param containsBox The variable that indicates if the cell contains a box
     * @param item The item that can possibly be located on the cell (this field can be null if there is no item on the cell)
     */
    public Cell(Coordinates coordinates, CellType type, boolean containsBox, Item item){
        if(coordinates == null || coordinates.getLine() < 0 || coordinates.getColumn() < 0){
            throw new IllegalArgumentException("The cell's coordinates are invalid");
        }

        checkCellType(type);

        this.coordinates = new Coordinates(coordinates.getLine(),coordinates.getColumn());
        //To avoid changing coordinates resulting in changing this.coordinates
        this.type = type;
        this.containsBox = containsBox;
        this.item = item;
    }

    /**
     * Returns the cell's type
     * @return The cell's type
     */
    public CellType getType(){
        return this.type;
    }

    /**
     * Returns the boolean that indicates if the cell contains a box
     * @return true if the cell contains a box or false otherwise
     */
    public boolean containsBox(){
        return this.containsBox;
    }

    /**
     * Returns the cell's symbol according to its attributes
     * @return the cell's symbol according to its attributes
     */
    public String getCellSymbol(){

        if(this.containsBox){
            return "🌑";
        }

        if(this.item == null){
            return this.type.getCellTypeSymbol();
        }

        return this.item.getItemSymbol();

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
     * Puts or removes a box from the cell
     * @param containsBox Indicated if a box will be added or removed from the cell
     */
    public void controlBox(boolean containsBox){
        this.containsBox = containsBox;
    }

    /**
     * Adds the item located on the cell to the player's inventory if it is an equipable, or use it directly if it is a consumable
     * @param level The level where the cell is located
     */
    public void pickUp(Level level){
        if(this.item != null){
            if(this.item instanceof Equipable && !level.addUsableToPlayer(this.item)){
                return ;
            
            } else if(this.item instanceof Consumable) {
                this.item.use(level);
            }

            this.item = null;
            //To empty the cell from its item
        }
    }

    /**
     * Returns a String that contains the cell's attributes
     * @return a String that contains the cell's attributes
     */
    @Override
    public String toString(){
        String string = "Cell :\n -> " + this.coordinates + "\n -> Type : " + this.type + "\n -> Contains box : " + this.containsBox;
        if(this.item != null){
            string += "\n -> Item : " + this.item.getName();
        }
        return string;
    }

    /**
     * Checks if a cell is equal to an object
     * @param obj The object that will be compared to the cell
     * @return true if they are equal, or false otherwise
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

    /**
     * Returns the hash code of the cell
     * @return the hash code of the cell
     */
    @Override
    public int hashCode(){
        return this.coordinates.hashCode();
    }
}