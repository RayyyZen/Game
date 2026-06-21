package com.app.model.level.wincondition;

import com.app.model.level.Level;

/**
 * The item condition class that has as a win condition to collect the end item
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public class ItemCondition extends Win implements WinCondition {

    /**
     * 
     */
    private static final String DESCRIPTION = "Collect the end item 🔮 !";

    private boolean endGame = false;

    public ItemCondition(){
        super(DESCRIPTION);
    }

    public void end(){
        this.endGame = true;
    }
    
    /**
     * Checks if the player achieved the win condition of a level
     * @param level The level that will be checked
     * @return True if the player achieved the win condition of the level, or false otherwise
     */
    public boolean win(Level level){
        return this.endGame;
    }
}