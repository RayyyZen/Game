package com.app.model.usable.item.equipable;

import com.app.model.level.Level;
import com.app.model.usable.Triggerable;
import com.app.model.usable.item.Item;

public abstract class Equipable extends Item implements Triggerable {
    
    public Equipable(String name, String symbol) {
        super(name,symbol);
    }

    public boolean shouldTrigger(Level level){
        return false;
    }
}
