package com.app.usable.item.equipable;

import com.app.level.Level;

public class Swap extends Equipable {

    private static final String NAME = "Swap";
    
    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "🎭";

    public Swap(){
        super(NAME,SYMBOL);
    }

    public void use(Level level) {
        level.swapWithRandomEnemy();
    }

}
