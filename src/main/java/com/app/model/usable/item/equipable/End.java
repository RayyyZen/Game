package com.app.model.usable.item.equipable;

import com.app.model.level.Level;

public class End extends Equipable {

    private static final String NAME = "End";
    
    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "🔮";

    public End(){
        super(NAME,SYMBOL);
    }

    @Override
    public boolean shouldTrigger(Level level){
        return true;
    }

    public void use(Level level) {
        level.endItemWinCondition();
    }
}
