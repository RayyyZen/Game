package com.app.usable.item.equipable;

import com.app.level.Level;
import com.app.usable.Triggerable;
import com.app.usable.item.Item;

public abstract class Equipable extends Item implements Triggerable {
    
    public Equipable(String name, String symbol) {
        super(name,symbol);
    }

    public boolean shouldTrigger(Level level){
        return false;
    }
}
