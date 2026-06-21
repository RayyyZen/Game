package com.app.model.usable.skill;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.cell.Coordinates;
import com.app.model.entity.Player;
import com.app.model.level.Level;

public class Teleportation extends Skill {

    private static final String NAME = "Teleportation";

    private static final String SYMBOL = "✨";

    public Teleportation(){
        super(NAME, SYMBOL);
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

    public boolean conditionToUnlock(Player player){
        return player.getNumberOfKills() >= 3;
    }
}
