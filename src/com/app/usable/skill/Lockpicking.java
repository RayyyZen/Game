package com.app.usable.skill;

import com.app.entity.Player;
import com.app.level.Level;
import com.app.usable.UseMode;

public class Lockpicking extends Skill {

    private static final UseMode MODE = UseMode.ONETIME;

    public Lockpicking(){
        super(MODE);
    }

    public void use(Level level){
        level.playerCanLockpick();
    }

    public boolean condition(Player player){
        return player.getScore() >= 100;
    }
}
