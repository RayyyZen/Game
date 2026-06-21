package com.app.model.level.wincondition;

import com.app.model.level.Level;

public class CoinCondition extends Win implements WinCondition {

    private static final String DESCRIPTION = "Collect all the coins to finish the level 📀 !";

    public CoinCondition(){
        super(DESCRIPTION);
    }
    
    /**
     * Checks if the player achieved the win condition of a level
     * @param level The level that will be checked
     * @return True if the player achieved the win condition of the level, or false otherwise
     */
    public boolean win(Level level){
        return level.getNumberOfCoins() == 0;
    }
}