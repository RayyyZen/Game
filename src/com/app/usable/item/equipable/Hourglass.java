package com.app.usable.item.equipable;

import com.app.level.Level;
import com.app.usable.UseMode;

public class Hourglass extends Equipable {
    
    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "⌛";

    private static final int NUMBEROFMOVEMENTS = 10;

    private static final UseMode MODE = UseMode.MANUAL;

    public Hourglass(){
        super(SYMBOL,MODE);
    }

    public void use(Level level) {
        level.blockEnemies(NUMBEROFMOVEMENTS);;
    }
}
