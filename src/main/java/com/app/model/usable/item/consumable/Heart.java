package com.app.model.usable.item.consumable;

import com.app.model.level.Level;

public class Heart extends Consumable {

    private static final String NAME = "HEART";
    
    /**
     * Number of hearts that the player gains from picking up a heart
     */
    private static final int HEARTS = 1;

    /**
     * The emoji symbol that represents each heart
     */
    private static final String SYMBOL = "❤ ";

    public Heart(){
        super(NAME,SYMBOL);
    }

    public void use(Level level){
        level.modifyPlayerHearts(HEARTS);
    }
}
