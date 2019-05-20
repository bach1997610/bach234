/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

/**
 * FXML Controller class
 *
 * @author binhm
 */
public class MainMenuController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML protected void handlePatientUpdate(ActionEvent event) {
        //System.out.println("Update patients");
        Scene patientFormScene=null;
        try {
            patientFormScene = MainController.myApp.loadForm("/views/PatientForm.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (patientFormScene != null) {
            MainController.stage.close();
            MainController.stage.setScene(patientFormScene);
            MainController.stage.show();
        } else {
            System.out.println("Không thể tải form bệnh nhân!");
        }
    }

    @FXML protected void handleAudioUpdate(ActionEvent event) {
        //System.out.println("Update patients");
        Scene scene=null;
        try {
            scene = MainController.myApp.loadForm("/views/AudioForm.fxml");
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (scene != null) {
            MainController.stage.close();
            MainController.stage.setScene(scene);
            MainController.stage.show();
        } else {
            System.out.println("Không thể tải form Audio!");
        }
    }
    
    @FXML protected void handleTreatement(ActionEvent event) {
       
    }
    
    @FXML protected void handleQuit(ActionEvent event) {
        System.out.println("Bye!");
        System.exit(0);
    }
    
    @FXML protected void handleStatistics(ActionEvent event) {
       
    }
}
