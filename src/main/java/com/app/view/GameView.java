package com.app.view;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.GroupLayout.Alignment;

import com.app.controller.MainController;
import com.app.model.display.View;
import com.app.model.game.Game;
import com.app.model.level.Direction;
import com.app.model.level.Level;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class GameView extends BorderPane {

    private Stack<KeyCode> pressedKeys = new Stack<>();

    private Direction keyToDirection(KeyCode key){

        switch(key){
            case UP, Z :
                return Direction.UP;

            case DOWN, S :
                return Direction.DOWN;

            case LEFT, Q :
                return Direction.LEFT;

            case RIGHT, D :
                return Direction.RIGHT;
            default:
                return Direction.NONE;
        }

    }

    private void gridRelayout(Level level){

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        int lines = level.getHeight();
        int columns = level.getWidth();

        DoubleBinding cellSize = Bindings.createDoubleBinding(() -> {
            double availableWidth = widthProperty().get() * 0.8;
            double availableHeight = heightProperty().get() * 0.8;
            double sizeFromWidth = availableWidth / columns;
            double sizeFromHeight = availableHeight / lines;
            return Math.min(sizeFromWidth, sizeFromHeight);
        }, widthProperty(), heightProperty());

        for (int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                String emoji = level.getCellSymbolInLevel(i, j);

                ImageView image = new ImageView(EmojiImage.getImage(emoji));

                image.setPreserveRatio(false);
                image.fitWidthProperty().bind(cellSize);
                image.fitHeightProperty().bind(cellSize);

                grid.setGridLinesVisible(true);

                grid.add(image, j, i);

            }
        }

        // Empêche le GridPane d'être étiré par le BorderPane
        
        grid.prefWidthProperty().bind(cellSize.multiply(columns));
        grid.setMaxWidth(Region.USE_PREF_SIZE);

        grid.prefHeightProperty().bind(cellSize.multiply(lines));
        grid.setMaxHeight(Region.USE_PREF_SIZE);


        setCenter(grid);
        BorderPane.setAlignment(grid, Pos.CENTER);



        GridPane inventory = new GridPane();
        inventory.setAlignment(Pos.CENTER);

        for(int i = 0; i < 5; i++){
            Label label = new Label(" " + (i + 1) + " ");

            inventory.add(label, i, 0);
            
            Image img = null;

            if(i < level.getPlayer().getInventorySize()){
                String emoji = level.getPlayer().getUsableSymbol(i);
                img = EmojiImage.getImage(emoji);
            }

            ImageView image = new ImageView(img);

            image.setPreserveRatio(false);
            image.fitWidthProperty().bind(cellSize);
            image.fitHeightProperty().bind(cellSize);
        
            inventory.add(image, i, 1);

            inventory.setGridLinesVisible(true);

        }

        inventory.setPadding(new Insets(2));

        setBottom(inventory);
        BorderPane.setAlignment(inventory, Pos.CENTER);

    }

    public GameView(MainController controller){

        Game game = controller.getGame();

        this.gridRelayout(game.getActualLevel());

        setFocusTraversable(true);
        requestFocus();

        Timeline loop = new Timeline();

        loop.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
                game.getActualLevel().moveAllEnemies();
                
                if(game.gameOver() || (game.win() && game.getActualIndex() == game.getNumberOfLevels() - 1)){
                    loop.stop();
                    controller.showEnd();
                    return;
                }

                if(game.win() && game.getActualIndex() < game.getNumberOfLevels() - 1){
                    try{
                    game.moveToNextLevel();
                    }catch(Exception ex){}
                }

                this.gridRelayout(game.getActualLevel()); 

            })
        );
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();

        setOnKeyPressed(event -> {
            KeyCode key = event.getCode();

            if (pressedKeys.contains(key)) {
                return;
            }

            pressedKeys.add(key);

            Direction direction = this.keyToDirection(key);

            game.movePlayer(direction);

            if(View.validInventoryIndex(game.getActualLevel().getPlayer(),event.getText())){
                int inventoryIndex = Integer.parseInt(event.getText()) - 1;
                game.useUsable(inventoryIndex); 
            }

            if(game.gameOver() || (game.win() && game.getActualIndex() == game.getNumberOfLevels() - 1)){
                loop.stop();
                controller.showEnd();
            }

            if(game.win() && game.getActualIndex() < game.getNumberOfLevels() - 1){
                try{
                game.moveToNextLevel();
                }catch(Exception ex){}
            }

            this.gridRelayout(game.getActualLevel());
        });

        setOnKeyReleased(event -> {
            if(!pressedKeys.isEmpty()){
                pressedKeys.pop();
            }
        });

        sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                requestFocus();
            }
        });

    }
    
}
