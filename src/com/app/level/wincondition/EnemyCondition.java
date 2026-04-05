package com.app.level.wincondition;

import com.app.level.Level;
import com.app.level.WinCondition;

public class EnemyCondition implements WinCondition {

    private static final String LABEL = "Eliminate all the enemies to finish the level !";
    
    public boolean win(Level level){
        return level.getNumberOfEnemies() == 0;
    }

    public String label(){
        return LABEL;
    }

}