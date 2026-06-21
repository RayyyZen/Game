package com.app.model.usable.skill;

import com.app.model.cell.Coordinates;
import com.app.model.entity.Player;
import com.app.model.level.Level;
import com.app.model.usable.Weapon;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends Skill implements Weapon {

    private static final String NAME = "Bomb";

    private static final int DAMAGE = -1;

    private static final String SYMBOL = "💣";

    private List<String> damagedEnemies;

    public Bomb(){
        super(NAME, SYMBOL);
        this.damagedEnemies = new ArrayList<>();
    }

    private int min(int a, int b){
        return a < b ? a : b;
    }

    private int max(int a, int b){
        return a > b ? a : b;
    }

    public void use(Level level){
        int height = level.getHeight();
        int width = level.getWidth();

        int line = level.getPlayerLine();
        int column = level.getPlayerColumn();

        int x1 = max(line - 1, 0);
        int x2 = min(line + 1, height - 1);
        int y1 = max(column - 1, 0);
        int y2 = min(column + 1, width - 1);

        this.damagedEnemies = new ArrayList<>();

        for(int i = x1; i <= x2; i++){
            for(int j = y1; j <= y2; j++){
                List<String> damagedEnemies = level.damageEnemiesOnCoordinates(new Coordinates(i,j),DAMAGE);
                if(!damagedEnemies.isEmpty()){
                    this.damagedEnemies.addAll(damagedEnemies);
                }
            }
        }
    }

    public boolean conditionToUnlock(Player player){
        return player.getNumberOfUsedSwords() >= 5;
    }

    public List<String> getDamagedEnemies(){
        return this.damagedEnemies;
    }

}
