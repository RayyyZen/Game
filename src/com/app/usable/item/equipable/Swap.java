package com.app.usable.item.equipable;

import com.app.level.Level;
import com.app.usable.UseMode;

public class Swap extends Equipable {
    
    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "🎭";

    private static final UseMode MODE = UseMode.MANUAL;

    public Swap(){
        super(SYMBOL,MODE);
    }

    public void use(Level level) {
        level.swapWithRandomEnemy();
    }

}
