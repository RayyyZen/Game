package com.app.model.usable.item.consumable;

import com.app.model.usable.item.Item;

/**
 * The consumable item interface
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Consumable extends Item {
    
    public Consumable(String name, String symbol){
        super(name,symbol);
    }

}
