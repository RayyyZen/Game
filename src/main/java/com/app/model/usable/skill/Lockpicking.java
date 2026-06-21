package com.app.model.usable.skill;

import com.app.model.entity.Player;
import com.app.model.level.Level;

public class Lockpicking extends Skill {

    private static final String NAME = "Lockpicking";

    private static final String SYMBOL = "🗝 ";

    private boolean used = false;

    public Lockpicking(){
        super(NAME, SYMBOL);
    }

    public void use(Level level){
        level.playerCanLockpick();
        this.used = true;
    }

    public boolean conditionToUnlock(Player player){
        return player.getScore() >= 100;
    }

    @Override
    public boolean shouldTrigger(Level level) {
        return !this.used;
    }
}
