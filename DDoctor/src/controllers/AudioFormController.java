/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import models.AudioEntity;
import models.Patient;

/**
 * FXML Controller class
 *
 * @author binhm
 */
public class AudioFormController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private MediaControl mediaControl;

    ObservableList<AudioEntity> data_audio;
    String currentPath = "";
    int currentItem = 0;
    //Media media; 
    MediaPlayer mediaPlayer = null;//
    private Duration duration;

    private boolean data_changed = false;
    private int selectedAudio = 0;    //index of selected audio

    @FXML
    TableView tv_audio;

    @FXML
    AnchorPane pane_mediaControl;

    //old
    //@FXML
    //Slider sl_volume;
    //@FXML
    //Slider sl_duration;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mediaControl = new MediaControl();
        pane_mediaControl.getChildren().add(mediaControl);

        TableColumn nameCol = new TableColumn("Tên");
        TableColumn pathCol = new TableColumn("Đường dẫn");

        tv_audio.getColumns().clear();
        tv_audio.getColumns().addAll(nameCol, pathCol);

        data_audio = FXCollections.observableArrayList();
        readFromFile(MainController.audioDBFile);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));

        tv_audio.setItems(data_audio);

        tv_audio.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                //System.out.println("Mouse clicked");
                currentItem = tv_audio.getSelectionModel().getSelectedIndex();

                selectedAudio = currentItem;
                if (selectedAudio < 0) {
                    return;
                }

                if (mediaPlayer != null) {
                    mediaPlayer = null;
                    mediaControl.close();
                }

                File mediaFile = new File(data_audio.get(selectedAudio).getPath());
                Media media = new Media(mediaFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);

                mediaControl.setMediaPlayer(mediaPlayer);
                mediaControl.init();
                //tf_firstName.setText(data_patient.get(selectedPatient).getFirstName());
                //tf_lastName.setText(data_patient.get(selectedPatient).getLastName());
            }
        ;
        }
    );

//        sl_volume.valueProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                if (sl_volume.isValueChanging()) {
//                    if (mediaPlayer != null) {
//                        mediaPlayer.setVolume(sl_volume.getValue() / 100.0);
//                        //sl_volume.setValue((int) Math.round(mediaPlayer.getVolume()* 100));
//                    }
//                }
//            }
//        });

//        sl_duration.valueProperty().addListener(new InvalidationListener() {
//            public void invalidated(Observable ov) {
//                if (sl_duration.isValueChanging()) {
//                    // multiply duration by percentage calculated by slider position
//                    if (mediaPlayer != null) {
//                        mediaPlayer.seek(duration.multiply(sl_duration.getValue() / 100.0));
//                    }
//                }
//            }
//        });

    }

    @FXML
    protected void handleClose(ActionEvent event) {
        //System.out.println("Close form now");
        if (data_changed) {
            Dialog<ButtonType> dlg = new Dialog<>();
            dlg.setContentText("Nội dung đã thay đổi, bạn có muốn lưu lại không?");
            ButtonType bt_yes = new ButtonType("Có", ButtonBar.ButtonData.YES);
            ButtonType bt_no = new ButtonType("Không", ButtonBar.ButtonData.NO);
            dlg.getDialogPane().getButtonTypes().addAll(bt_yes, bt_no);

            Optional<ButtonType> result = dlg.showAndWait();
            if (result.isPresent() && result.get() == bt_yes) {
                save();
            }
        }
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        MainController.stage.close();
        MainController.myApp.showMainMenu();

    }

    @FXML
    protected void handleAdd(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Audio File");
        if (!currentPath.isEmpty()) {
            File initFolder = new File(currentPath);

            fileChooser.setInitialDirectory(initFolder);
        }

        File file = fileChooser.showOpenDialog(MainController.stage);
        if (file != null) {
            AudioEntity e = new AudioEntity();
            e.setName(file.getName());
            currentPath = file.getParent();
            System.out.println(currentPath);
            e.setPath(file.getPath());
            data_audio.add(e);
            data_changed = true;

        }

    }

    @FXML
    protected void handleSave(ActionEvent event) {
        //System.out.println("Close form now");
        save();

    }

    private void save() {
        if (data_audio.size() > 0) {
            saveToFile(MainController.audioDBFile);
            data_changed = false;
        }
    }

    @FXML
    protected void handleRemove(ActionEvent event) {
        //System.out.println("Close form now");
        if (data_audio != null && currentItem >= 0 && currentItem < data_audio.size()) {
            data_audio.remove(currentItem);
            data_changed = true;
            if (currentItem >= data_audio.size()) {
                currentItem--;
            }
            tv_audio.refresh();
        }
    }

//    @FXML
//    protected void handlePlay(ActionEvent event) {
//        //System.out.println("Playing " + data_patient.get(currentItem).getPath());
//
//        Media media = new Media(new File(data_audio.get(currentItem).getPath()).toURI().toString());
//        if (mediaPlayer != null) {
//            mediaPlayer.dispose();
//        }
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setAutoPlay(true);
//
////        mediaPlayer.setOnReady(new Runnable() {
////            public void run() {
////                duration = mediaPlayer.getMedia().getDuration();
////                updateValues();
////            }
////        });
//
////        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
////            public void invalidated(Observable ov) {
////                updateValues();
////            }
////        });
//
//    }

//    @FXML
//    protected void handleStop(ActionEvent event) {
//        //System.out.println("Close form now");
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//        }
//    }

    private void readFromFile(String fileName) {
        FileInputStream file;
        File f = new File(fileName);
        if (!f.exists()) {
            return;
        }

        try {
            file = new FileInputStream(fileName);

            ObjectInputStream in = new ObjectInputStream(file);
            ArrayList<AudioEntity> list = new ArrayList<>();
            list = (ArrayList<AudioEntity>) in.readObject();
            for (AudioEntity p : list) {
                data_audio.add(p);
            }
            in.close();
            file.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(PatientFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void saveToFile(String fileName) {
        FileOutputStream file;
        try {
            file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            ArrayList<AudioEntity> list = new ArrayList<>();
            for (AudioEntity p : data_audio) {
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

//    protected void updateValues() {
//        if (sl_duration != null && sl_volume != null) {
//            Platform.runLater(new Runnable() {
//                public void run() {
//                    Duration currentTime = mediaPlayer.getCurrentTime();
//                    //currentTime.d
//                    //playTime.setText(formatTime(currentTime, duration));
//                    sl_duration.setDisable(duration.isUnknown());
//                    if (!sl_duration.isDisabled()
//                            && duration.greaterThan(Duration.ZERO)
//                            && !sl_duration.isValueChanging()) {
//                        sl_duration.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
//                    }
//                    if (!sl_volume.isValueChanging()) {
//                        sl_volume.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
//                    }
//                }
//            });
//        }
//    }
}
