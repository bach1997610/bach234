/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 *
 * @author binhm
 */
public class testForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        File mediaFile = new File("D:/tmp/AudioFiles/DiVang-TrinhNamSon.mp3");
        Media media = new Media(mediaFile.toURI().toString());
        MediaPlayer mp = new MediaPlayer(media);

        MediaControl mc = new MediaControl();
        mc.setMediaPlayer(mp);
        mc.init();

        Scene scene = new Scene(mc, 400, 250);

        primaryStage.setTitle("Test form!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
