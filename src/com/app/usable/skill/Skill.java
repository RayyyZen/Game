package com.app.usable.skill;

import com.app.entity.Player;
import com.app.level.Level;
import com.app.usable.Triggerable;
import com.app.usable.Usable;
import com.app.usable.UseMode;

/**
 * The skill class
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Skill implements Usable, Triggerable, Comparable<Skill> {

    private final UseMode useMode;
    
    public Skill(UseMode useMode) {
        this.useMode = useMode;
    }

    public UseMode getUseMode(){
        return this.useMode;
    }

    public boolean isTriggerable(Level level){
        return useMode == UseMode.AUTOMATIC;
    }

    public abstract void use(Level level);

    public abstract boolean condition(Player player);

    @Override
    public int compareTo(Skill other){
        return this.getClass().getSimpleName().compareTo(other.getClass().getSimpleName());
    }
}
