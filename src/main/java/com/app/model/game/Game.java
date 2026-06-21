package com.app.model.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.model.level.Direction;
import com.app.model.level.Level;
import com.app.model.entity.Player;

public class Game {

    private List<String> filesName;

    private Level actualLevel;

    private Player player;

    private int actualIndex;

    public Game(String playerName, List<String> filesName) throws IOException {
        if(filesName == null || filesName.isEmpty()){
            throw new IllegalStateException("Problem in args");
        }

        this.filesName = new ArrayList<>(filesName);

        this.player = new Player(playerName);
        this.actualIndex = 0;

        this.actualLevel = new Level(filesName.get(this.actualIndex), player);
    }

    public int getNumberOfLevels(){
        return this.filesName.size();
    }

    public void moveToNextLevel() throws IOException{
        if(this.actualIndex < this.filesName.size() - 1){
            this.actualIndex++;
        }
        this.actualLevel = new Level(filesName.get(this.actualIndex), player);
    }

    public void movePlayer(Direction direction){
        actualLevel.movePlayer(direction);
        actualLevel.effect();
        player.unlockNewSkills(actualLevel);
    }

    public void moveAllEnemies(){
        actualLevel.moveAllEnemies();
        actualLevel.effect();
        player.unlockNewSkills(actualLevel);
    }

    public boolean gameOver(){
        return actualLevel.gameOver();
    }

    public boolean win(){
        return actualLevel.win();
    }

    public void useUsable(int index){
        actualLevel.use(index);
    }

    public Level getActualLevel(){
        return this.actualLevel;
    }

    public int getActualIndex(){
        return this.actualIndex;
    }
}
