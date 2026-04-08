package com.app.usable.skill;

import com.app.entity.Player;
import com.app.level.Level;

public class Lockpicking extends Skill {

    private static final String NAME = "Lockpicking";

    private boolean used = false;

    public Lockpicking(){
        super(NAME);
    }

    public void use(Level level){
        level.playerCanLockpick();
        this.used = true;
    }

    public boolean condition(Player player){
        return player.getScore() >= 100;
    }

    @Override
    public boolean shouldTrigger(Level level) {
        return !this.used;
    }
}
