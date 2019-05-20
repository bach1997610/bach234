/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import controllers.PatientFormController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author binhm
 */
public class Library {
    //Fading node in duration seconds
    public static void fadingObject(Node node, double duration) {
        FadeTransition ft = new FadeTransition(Duration.seconds(duration), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();
    }
    public static <T> ObservableList<T> readFromFile(String fileName) {
        FileInputStream file;
        File f = new File(fileName);
        ObservableList<T> data = FXCollections.observableArrayList();
        if (!f.exists()) {
            return data;
        }

        try {
            file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);
            ArrayList<T> list = new ArrayList<>();
            list = (ArrayList<T>) in.readObject();
            for (T p : list) {
                data.add(p);
            }
            in.close();
            file.close();
            return data;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;

    }

    public static <T> void saveToFile(ObservableList<T> data, String fileName) {
        FileOutputStream file;
        try {
            file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ArrayList<T> list = new ArrayList<>();
            for (T p : data) {
                list.add(p);
            }

            out.writeObject(list);
            out.close();
            file.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
