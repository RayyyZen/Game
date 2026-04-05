package com.app.usable.skill;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.cell.Coordinates;
import com.app.entity.Player;
import com.app.level.Level;
import com.app.usable.UseMode;

public class Teleportation extends Skill {

    private static final UseMode MODE = UseMode.MANUAL;

    public Teleportation(){
        super(MODE);
    }

    public void use(Level level){
        int height = level.getHeight();
        int width = level.getWidth();

        List<Coordinates> empty = new ArrayList<>();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(level.isEmptyCell(i,j)){
                    empty.add(new Coordinates(i,j));
                }
            }
        }

        if(empty.isEmpty()){
            return ;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0,empty.size());

        Coordinates randomCoordinates = empty.get(randomIndex);

        level.movePlayer(randomCoordinates.getLine(),randomCoordinates.getColumn());
    }

    public boolean condition(Player player){
        return player.getNumberOfKills() >= 3;
    }
}
