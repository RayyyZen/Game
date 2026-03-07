package com.app.entity.enemy;

import com.app.cell.Cell;
import com.app.entity.Entity;
import com.app.level.*;

public abstract class Enemy extends Entity {

    protected static int numberOfEnemies = 0;
    
    public Enemy(String name, int hearts){
        super(name,hearts);
        numberOfEnemies++;
    }

    public Enemy(int hearts){
        this("Enemy" + (numberOfEnemies + 1),hearts);
    }

    public abstract Direction getDirection(Level level);

    public abstract boolean validMovement(Cell cell);

    public abstract String getSymbol();

    public abstract int getDamage();
}
