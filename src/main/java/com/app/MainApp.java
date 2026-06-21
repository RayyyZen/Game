package com.app;

import com.app.controller.MainController;
import com.app.model.entity.Player;
import com.app.model.game.Game;
import com.app.model.level.Level;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main application class
 * @version 5.0
 * @since 5.0
 * @author Rayane
 */
public class MainApp extends Application {

    /**
     * The main app constructor
     */
    public MainApp(){}

    /**
     * The start method of the application
     * @param stage The main stage
     */
    @Override
    public void start(Stage stage) {
        Game game = null;

        try{
            game = new Game("a", getParameters().getRaw());
        } catch(Exception e) {}

        MainController controller = new MainController(stage, game);

        controller.showMenu();
    }

    /**
     * The main method
     * @param args The arguments of the main method
     */
    public static void main(String[] args) {
        launch(args);
    }
}
