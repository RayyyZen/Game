package com.app.usable.item.consumable;

import com.app.level.Level;

public class Heart extends Consumable {
    
    /**
     * Number of hearts that the player gains from picking up a heart
     */
    private static final int HEARTS = 1;

    /**
     * The emoji symbol that represents each heart
     */
    private static final String SYMBOL = "❤ ";

    public Heart(){
        super(SYMBOL);
    }

    public void use(Level level){
        level.modifyPlayerHearts(HEARTS);
    }
}
