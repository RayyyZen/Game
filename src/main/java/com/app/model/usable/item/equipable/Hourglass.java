package com.app.model.usable.item.equipable;

import com.app.model.level.Level;

public class Hourglass extends Equipable {

    private static final String NAME = "Hourglass";
    
    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "⏱ ";

    private static final int NUMBEROFMOVEMENTS = 10;

    public Hourglass(){
        super(NAME,SYMBOL);
    }

    public void use(Level level) {
        level.blockEnemies(NUMBEROFMOVEMENTS);
    }
}
