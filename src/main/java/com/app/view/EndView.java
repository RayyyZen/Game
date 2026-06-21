package com.app.view;

import com.app.controller.MainController;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class EndView extends BorderPane {

    public EndView(MainController controller){

        Label label = new Label("Game Over");



        setCenter(label);

    }
    
}
