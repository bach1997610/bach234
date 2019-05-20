/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author binhm
 */
public class MainController extends Application {

    public static Stage stage;
    public static MainController myApp;
    public static final String patientDBFile = "src/db/patient.dat";
    public static final String audioDBFile = "src/db/audio.dat";
    public static final String treatementDBFile = "src/db/treatement.dat";
    public static final String deviceDBFile = "src/db/device.dat";

    

    public Scene loadForm(String source) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(source));

        Scene scene = new Scene(root);
        return scene;
    }

    public void showMainMenu() {
        Scene mainMenuScene = null;
        try {
            mainMenuScene = loadForm("/views/MainMenu.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (mainMenuScene != null) {
            stage.setScene(mainMenuScene);
            stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> {
                event.consume();
            });
            stage.show();
        } else {
            System.out.println("Không thể tải form menu");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainController.stage = stage;
        myApp = this;
        showMainMenu();

    }

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
