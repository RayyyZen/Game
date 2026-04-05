package com.app.usable.item.consumable;

import com.app.level.Level;
import com.app.usable.item.Item;

/**
 * The consumable item interface
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Consumable extends Item {
    
    public Consumable(String symbol){
        super(symbol);
    }

    public abstract void use(Level level);
}
