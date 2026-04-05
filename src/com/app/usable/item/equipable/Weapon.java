package com.app.usable.item.equipable;

import com.app.level.Level;
import com.app.usable.UseMode;

public class Weapon extends Equipable {

    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "🗡 ";

    private static final int DAMAGE = -1;

    private static final UseMode MODE = UseMode.AUTOMATIC;

    public Weapon(){
        super(SYMBOL,MODE);
    }

    @Override
    public boolean shouldTrigger(Level level) {
        return level.playerEnemyCollision();
    }

    @Override
    public void use(Level level) {
        level.damageEnemiesOnCollision(DAMAGE);
    }

}
