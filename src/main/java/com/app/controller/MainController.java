package com.app.controller;

import com.app.model.game.Game;
import com.app.model.level.Level;
import com.app.view.EndView;
import com.app.view.GameView;
import com.app.view.MenuView;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {

    private Stage stage;
    
    private Game game;

    public MainController(Stage stage, Game game){
        this.stage = stage;
        this.game = game;
    }

    private void setScene(Pane pane){
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public Game getGame(){
        return this.game;
    }

    public void showMenu(){
        MenuView menu = new MenuView(this);
        this.setScene(menu);
    }

    public void showGame(){
        GameView game = new GameView(this);
        this.setScene(game);
    }

    public void showEnd(){
        EndView end = new EndView(this);
        this.setScene(end);
    }
}