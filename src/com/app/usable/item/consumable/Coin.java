package com.app.usable.item.consumable;

import com.app.level.Level;

public class Coin extends Consumable {

    private static final String NAME = "Coin";
    
    /**
     * Number of points (score) that the player gains from picking up a coin
     */
    private static final int COIN = 90;

    /**
     * The emoji symbol that represents each coin
     */
    private static final String SYMBOL = "📀";

    public Coin(){
        super(NAME,SYMBOL);
    }

    public void use(Level level){
        level.removeCoin();
        level.modifyPlayerScore(COIN);
    }
}