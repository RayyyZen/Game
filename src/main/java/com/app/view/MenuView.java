package com.app.view;

import com.app.controller.MainController;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class MenuView extends BorderPane {
    

    public MenuView(MainController controller){

        Button game = new Button("Game");

        game.setOnAction(e -> {
            controller.showGame();
        });

        setCenter(game);

    }


}
