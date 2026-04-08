package com.app.usable.skill;

import com.app.entity.Player;
import com.app.level.Level;
import com.app.usable.Triggerable;
import com.app.usable.Usable;

/**
 * The skill class
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Skill implements Usable, Triggerable, Comparable<Skill> {

    private final String name;

    public Skill(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

   public boolean shouldTrigger(Level level){
        return false;
    }

    public abstract boolean condition(Player player);

    @Override
    public int compareTo(Skill other){
        return this.name.compareTo(other.name);
    }
}
