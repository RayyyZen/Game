package com.app.usable.item.equipable;

import com.app.level.Level;
import com.app.usable.Triggerable;
import com.app.usable.UseMode;
import com.app.usable.item.Item;

public abstract class Equipable extends Item implements Triggerable {

    private final UseMode useMode;
    
    public Equipable(String symbol, UseMode useMode) {
        super(symbol);
        this.useMode = useMode;
    }

    public UseMode getUseMode(){
        return this.useMode;
    }

    protected boolean shouldTrigger(Level level){
        return false;
    }

    public boolean isTriggerable(Level level){
        return this.useMode == UseMode.AUTOMATIC && this.shouldTrigger(level);
    }

    public abstract void use(Level level);
}
