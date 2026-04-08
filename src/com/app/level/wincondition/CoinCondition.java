package com.app.level.wincondition;

import com.app.level.Level;
import com.app.level.WinCondition;

public class CoinCondition implements WinCondition {

    private static final String LABEL = "Collect all the coins to finish the level 📀 !";
    
    public boolean win(Level level){
        return level.getNumberOfCoins() == 0;
    }

    public String label(){
        return LABEL;
    }

}
