package com.app.level.wincondition;

import com.app.level.Level;
import com.app.level.WinCondition;

public class ItemCondition implements WinCondition {

    private static final String LABEL = "Collect the end item 🔮 !";

    private boolean endGame = false;

    public void end(){
        this.endGame = true;
    }
    
    public boolean win(Level level){
        return this.endGame;
    }

    public String label(){
        return LABEL;
    }

}