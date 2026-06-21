package com.app.model.usable.skill;

import com.app.model.entity.Player;
import com.app.model.level.Level;
import com.app.model.usable.Triggerable;
import com.app.model.usable.Usable;

/**
 * The skill class
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Skill implements Usable, Triggerable, Comparable<Skill> {

    private final String name;

    private final String symbol;

    public Skill(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    public String getName(){
        return this.name;
    }

    public String getSymbol(){
        return this.symbol;
    }

   public boolean shouldTrigger(Level level){
        return false;
    }

    public abstract boolean conditionToUnlock(Player player);

    @Override
    public int compareTo(Skill other){
        return this.name.compareTo(other.name);
    }
}
