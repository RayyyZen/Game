package com.app.model.cell;

/**
 * The enumeration that contains the type of each cell on the level's grid ant its representing symbol
 */
public enum CellType {

    /**
     * An empty cell
     */
    EMPTY("  "),

    /**
     * A wall that can't be crossed
     */
    WALL("🔳"),

    /**
     * A trap that can cause damage
     */
    TRAP("🔗"),

    /**
     * A locked door that can't be crossed
     */
    LOCKED_DOOR("🔐"),

    /**
     * A hole that can be closed by a box
     */
    HOLE("💫");

    /**
     * The symbol representing a each cell's type
     */
    private final String symbol;

    /**
     * The cell type constructor
     * @param symbol The symbol representing the cell type
     */
    private CellType(String symbol){
        this.symbol = symbol;
    }

    /**
     * Returns the symbol representing the cell type
     * @return the symbol representing the cell type
     */
    public String getCellTypeSymbol(){
        return this.symbol;
    }
}