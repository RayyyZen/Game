package com.app;

public class Coordinates {
    private int line;
    private int column;

    public Coordinates(int line, int column){
        if(line < 0 || column < 0){
            throw new RuntimeException("The coordinates on the grid can't be negative");
        }

        this.line = line;
        this.column = column;
    }

    public int getLine(){
        return this.line;
    }

    public int getColumn(){
        return this.column;
    }

    public void setLine(int line){
        if(line < 0){
            throw new RuntimeException("The coordinates on the grid can't be negative");
        }
        this.line = line;
    }

    public void setColumn(int column){
        if(column < 0){
            throw new RuntimeException("The coordinates on the grid can't be negative");
        }
        this.column = column;
    }
}
