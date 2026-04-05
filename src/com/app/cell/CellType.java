package com.app.cell;

/**
 * The enumeration that contains the type of each cell on the level's grid
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

    private final String symbol;

    private CellType(String symbol){
        this.symbol = symbol;
    }

    public String getCellTypeSymbol(){
        return this.symbol;
    }
    
}