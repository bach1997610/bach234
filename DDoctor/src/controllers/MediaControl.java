/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author binhm
 */
public class MediaControl extends BorderPane {

    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    private VBox mediaBar;

    private final Button playButton;
    private final Button stopButton;
    private final Button fastRewindButton;
    private final Button fastForwardButton;

    private Label timeLabel;
    private Label volumeLabel;

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isAtEndOfMedia() {
        return atEndOfMedia;
    }

    public void setAtEndOfMedia(boolean atEndOfMedia) {
        this.atEndOfMedia = atEndOfMedia;
    }

    //public MediaControl(final MediaPlayer mp) {
    public MediaControl() {
        //this.mediaPlayer = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        //mediaView = new MediaView(mp);
//        Pane mvPane = new Pane();
//        mvPane.getChildren().add(mediaView);
//        mvPane.setStyle("-fx-background-color: black;");
//        setCenter(mvPane);

//        mediaBar = new HBox();
//        mediaBar.setAlignment(Pos.CENTER);
//        mediaBar.setPadding(new Insets(10, 10, 10, 10));
//        BorderPane.setAlignment(mediaBar, Pos.CENTER);
        mediaBar = new VBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(10, 10, 10, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);

        HBox sub_mediaBar1 = new HBox();
        sub_mediaBar1.setAlignment(Pos.CENTER);
        sub_mediaBar1.setPadding(new Insets(10, 10, 10, 10));

        VBox sub_mediaBar2 = new VBox();
        sub_mediaBar2.setAlignment(Pos.CENTER);
        sub_mediaBar2.setPadding(new Insets(10, 10, 10, 10));
        playButton = new Button(">");
        playButton.setMinWidth(40);
        playButton.setMaxWidth(60);
        Font butFont = new Font("Arial Bold", USE_PREF_SIZE);
        //butFont.
        playButton.setFont(butFont);

        stopButton = new Button("\u20E2");
        stopButton.setMinWidth(40);
        stopButton.setMaxWidth(60);
        stopButton.setFont(butFont);
        
        fastRewindButton = new Button("<<");
        fastRewindButton.setMinWidth(40);
        fastRewindButton.setMaxWidth(60);
        fastRewindButton.setFont(butFont);

        fastForwardButton = new Button(">>");
        fastForwardButton.setMinWidth(40);
        fastForwardButton.setMaxWidth(60);
        fastForwardButton.setFont(butFont);
        
        sub_mediaBar1.getChildren().addAll(fastRewindButton,playButton, stopButton, fastForwardButton);

        mediaBar.getChildren().addAll(sub_mediaBar1, sub_mediaBar2);

        HBox sub_mediaBar2_1 = new HBox();
        sub_mediaBar2_1.setAlignment(Pos.CENTER);
        sub_mediaBar2_1.setPadding(new Insets(10, 10, 10, 10));
        
        HBox sub_mediaBar2_2 = new HBox();
        sub_mediaBar2_2.setAlignment(Pos.CENTER);
        sub_mediaBar2_2.setPadding(new Insets(10, 10, 10, 10));
        
        sub_mediaBar2.getChildren().addAll(sub_mediaBar2_1,sub_mediaBar2_2);
        // Add spacer
        //Label spacer = new Label("   ");
        //mediaBar.getChildren().add(spacer);
// Add Time label
        timeLabel = new Label("Time: ");
        sub_mediaBar2_1.getChildren().add(timeLabel);

// Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(70);

        sub_mediaBar2_1.getChildren().add(timeSlider);

// Add Play label
        playTime = new Label();
        playTime.setPrefWidth(100);
        playTime.setMinWidth(50);
        sub_mediaBar2_1.getChildren().add(playTime);

// Add the volume label
        volumeLabel = new Label("Vol: ");
        sub_mediaBar2_2.getChildren().add(volumeLabel);

// Add Volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(70);
        volumeSlider.setMinWidth(50);

        sub_mediaBar2_2.getChildren().add(volumeSlider);

        setBottom(mediaBar);

    }

    //initialize after mediaPlayer has been set (not null)
    public void init() {
        if (mediaPlayer == null) {
            return;
        }

        mediaPlayer.currentTimeProperty().addListener((Observable ov) -> {
            updateValues();
        });

//        mediaPlayer.setOnPlaying(() -> {
//            if (stopRequested) {
//                mediaPlayer.pause();
//                stopRequested = false;
//            } else {
//                playButton.setText("||");
//            }
//        });

        mediaPlayer.setOnPaused(() -> {
            //System.out.println("onPaused");
            playButton.setText(">");
            updateValues();
        });

        mediaPlayer.setOnReady(() -> {
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
        });

        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mediaPlayer.setOnEndOfMedia(() -> {
            if (!isRepeat()) {
                playButton.setText(">");
                stopRequested = true;
                setAtEndOfMedia(true);
                
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.pause();
                updateValues();
            }
        });
        mediaPlayer.setOnStopped(() -> {
            playButton.setText(">");
            stopRequested = true;
            //setAtEndOfMedia(true);
            updateValues();
        });

        playButton.setOnAction((ActionEvent e) -> {
            Status status = mediaPlayer.getStatus();

//            if (status == Status.UNKNOWN || status == Status.HALTED) {
//                // don't do anything in these states
//                return;
//            }
            if (status == Status.PAUSED
                    || status == Status.READY
                    || status == Status.STOPPED) {
                // rewind the movie if we're sitting at the end
                if (isAtEndOfMedia()) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    setAtEndOfMedia(false);
                }
                playButton.setText("||");
                mediaPlayer.play();
            } else {
                playButton.setText(">");
                mediaPlayer.pause();
            }
            updateValues();
        });
        stopButton.setOnAction((event) -> {
            Status status = mediaPlayer.getStatus();
            if (status == Status.PLAYING || status == Status.READY || status== Status.PAUSED) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.pause();
                
            }
        });
        fastRewindButton.setOnAction((event) -> {
            Status status = mediaPlayer.getStatus();
            if (status == Status.PLAYING ||status==Status.PAUSED) {
                Duration curTime = mediaPlayer.getCurrentTime();
                curTime = curTime.subtract(new Duration(5*1000.0));
                if (curTime.lessThan(Duration.ZERO))
                    curTime = Duration.ZERO;
                mediaPlayer.seek(curTime);
                //mediaPlayer.pause();
                updateValues();
                
            }
        });
        
        fastForwardButton.setOnAction((event) -> {
            Status status = mediaPlayer.getStatus();
            if (status == Status.PLAYING ||status==Status.PAUSED) {
                Duration curTime = mediaPlayer.getCurrentTime();
                curTime = curTime.add(new Duration(5*1000.0));
                if (curTime.greaterThan(duration))
                    curTime = duration;
                mediaPlayer.seek(curTime);
                //mediaPlayer.pause();
                updateValues();
                
            }
        });
        
        volumeSlider.valueProperty().addListener((Observable ov) -> {
            if (volumeSlider.isValueChanging()) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
            }
        });

        timeSlider.valueProperty().addListener((Observable ov) -> {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                updateValues();
            }
        });
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(() -> {
                Duration currentTime = mediaPlayer.getCurrentTime();
                playTime.setText(formatTime(currentTime, duration));
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled()
                        && duration.greaterThan(Duration.ZERO)
                        && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                }
                if (!volumeSlider.isValueChanging()) {
                    volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume()
                            * 100));
                }
            });
        }
    }

    //convert Duration format to time format hh:mm:ss
    private static String convertDuration2Time(Duration duration) {
        int intDuration = (int) Math.floor(duration.toSeconds());
        int durationHours = intDuration / (60 * 60);
        if (durationHours > 0) {
            intDuration -= durationHours * 60 * 60;
        }
        int durationMinutes = intDuration / 60;
        int durationSeconds = intDuration - durationHours * 60 * 60
                - durationMinutes * 60;
        String s = String.format("%02d", durationSeconds);
        if (durationMinutes>0) s = String.format("%02d", durationMinutes) + ":" + s;
        if (durationHours>0) s = String.format("%02d", durationHours) + ":" + s;
        return s;
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        return convertDuration2Time(elapsed) + "/" + convertDuration2Time(duration);

    }
    public void close(){
        if (mediaPlayer!=null) mediaPlayer.dispose();
        mediaPlayer = null;
    }
}
