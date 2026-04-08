package com.app.usable.item.equipable;

import java.util.ArrayList;
import java.util.List;

import com.app.cell.Coordinates;
import com.app.level.Level;
import com.app.usable.Weapon;

public class Sword extends Equipable implements Weapon {

    private static final String NAME = "Sword";

    /**
     * The emoji symbol that represents each weapon
     */
    private static final String SYMBOL = "🗡 ";

    private static final int DAMAGE = -1;

    private List<String> damagedEnemies;

    public Sword(){
        super(NAME,SYMBOL);
        this.damagedEnemies = new ArrayList<>();
    }

    @Override
    public boolean shouldTrigger(Level level) {
        return level.playerEnemyCollision();
    }

    @Override
    public void use(Level level) {
        int line = level.getPlayerLine();
        int column = level.getPlayerColumn();

        this.damagedEnemies = level.damageEnemiesOnCoordinates(new Coordinates(line,column),DAMAGE);

        level.incrementPlayerNumberOfUsedSwords();
    }

    public List<String> getDamagedEnemies(){
        return this.damagedEnemies;
    }

}
