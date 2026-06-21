package com.app.model.level.wincondition;


public abstract class Win {
    
    private final String description;

    public Win(String description){
        this.description = description;
    }

    /**
     * Returns the description of the win condition
     * @return the description of the win condition
     */
    public String description(){
        return this.description;
    }
}