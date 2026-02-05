package com.app;

/**
 * The coordinates class that contains a specific position (line and column) on the grid that contains the level
 * @version 2.0 (Second world)
 * @since 2.0
 * @author Rayane
 */
public class Coordinates {

    /**
     * A specific line on the grid
     */
    private int line;

    /**
     * A specific column on the grid
     */
    private int column;

    /**
     * Checks if the line and column given as arguments are positive and throws a not checked exception in case they are not
     * @param line A specific line on the grid
     * @param column A specific column on the grid
     */
    public void checkCoordinates(int line, int column){
        if(line < 0 || column < 0){
            throw new IllegalArgumentException("The coordinates on the grid can't be negative");
        }
    }

    /**
     * The coordinates constructor that takes as arguments 
     * @param line A specific line on the grid
     * @param column A specific column on the grid
     */
    public Coordinates(int line, int column){
        checkCoordinates(line,column);

        this.line = line;
        this.column = column;
    }

    /**
     * Returns the line on which the point is located
     * @return The line on which the point is located
     */
    public int getLine(){
        return this.line;
    }

    /**
     * Returns the column on which the point is located
     * @return The column on which the point is located
     */
    public int getColumn(){
        return this.column;
    }

    /**
     * Modify the line on which the point is located
     * @param line A specific line on the grid
     */
    public void setLine(int line){
        checkCoordinates(line,this.column);

        this.line = line;
    }

    /**
     * Modify the column on which the point is located
     * @param column A specific column on the grid
     */
    public void setColumn(int column){
        checkCoordinates(this.line,column);

        this.column = column;
    }
}