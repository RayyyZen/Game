package com.app.usable.item.equipable;

import com.app.level.Level;
import com.app.usable.UseMode;

public class End extends Equipable {
    
    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "🔮";

    private static final UseMode MODE = UseMode.AUTOMATIC;

    public End(){
        super(SYMBOL,MODE);
    }

    protected boolean shouldTrigger(Level level){
        return true;
    }

    public void use(Level level) {
        level.endItemWinCondition();
    }
}
