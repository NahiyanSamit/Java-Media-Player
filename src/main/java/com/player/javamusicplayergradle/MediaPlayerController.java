//Main controller class for media player

package com.player.javamusicplayergradle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



//Class that control entire functionality of the media player Application
public class MediaPlayerController
{

    @FXML
    private MediaView MediaView;

    private Media media;

    private MediaPlayer mediaPlayer;

    private boolean isPlayed = false;

    @FXML
    private Slider slider;

    @FXML
    private Slider Volume;

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblDuration;


    // Method to handle the event of selection of a media file
    @FXML
    void SelectMedia(ActionEvent event)
    {
        try
        {

            // Set up a file chooser for selecting media files with proper condition

            FileChooser.ExtensionFilter ex = new FileChooser.ExtensionFilter("Media File: ","*.mp3","*.wav","*.mp4");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(ex);
            fileChooser.setTitle("Select Media");

            //After a file selected dialog box shows the file name
            File selectedFile = fileChooser.showOpenDialog(null);


            //Set initial extension as null
            String extension = null;


            // Check if a file is selected
            if(selectedFile != null)
            {

                // Extract file extension
                String url = selectedFile.toURI().toString();
                //System.out.println(url);

                String fileName = selectedFile.getName();
                //System.out.println(fileName);

                int index = url.lastIndexOf('.');

                if(index > 0)
                {
                    extension = url.substring(index + 1);

                }

                media = new Media(url);


                // Check if a media is already open, and dispose of the old media
                if(mediaPlayer != null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                }

                mediaPlayer = new MediaPlayer(media);


                // Set media player to the media view
                MediaView.setMediaPlayer(mediaPlayer);


                // Configure UI based on the file extension
                assert extension != null;
                Path path = Paths.get("src/main/resources/Icon/Play.png");


                if(extension.equals("mp3") || extension.equals("wav"))
                {
                    isPlayed = false;
                    btnPlay.setGraphic(new ImageView(new Image(Files.newInputStream(path))));
                    Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

                    stage.setTitle(fileName);

                    stage.setHeight(150);
                    stage.setWidth(650);
                    stage.setMinHeight(150);
                    stage.setMinWidth(650);
                }

                if(extension.equals("mp4"))
                {
                    isPlayed = false;
                    btnPlay.setGraphic(new ImageView(new Image(Files.newInputStream(path))));
                    Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

                    stage.setTitle(fileName);

                    stage.setHeight(600);
                    stage.setWidth(850);
                    stage.setMinHeight(450);
                    stage.setMinWidth(650);
                }


                // Update the duration label when the media is played
                mediaPlayer.currentTimeProperty().addListener((observableValue) -> {

                    double total = mediaPlayer.getTotalDuration().toSeconds();
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    slider.setValue(current);

                    int total_hour,total_minute,total_second;
                    int current_hour,current_Minute,current_second;

                    total_hour = (int )total / 3600;
                    total_minute = ((int) total % 3600) / 60;
                    total_second = (int) total % 60 ;

                    current_hour = (int )current / 3600;
                    current_Minute = ((int) current % 3600) / 60;
                    current_second = (int) current % 60;

                    lblDuration.setText("Duration: " + current_hour + ":" + current_Minute +":"+current_second + " / " + total_hour + ":" + total_minute+":" + total_second);

                });

                // Set up the UI when the media is ready
                mediaPlayer.setOnReady(() -> {

                    Duration totalDuration = media.getDuration();
                    slider.setMax(totalDuration.toSeconds());

                    int total_hour = (int) mediaPlayer.getTotalDuration().toSeconds() / 3600;
                    int total_minute = ((int) mediaPlayer.getTotalDuration().toSeconds() % 3600) / 60;
                    int total_second = (int) mediaPlayer.getTotalDuration().toSeconds() % 60 ;

                    lblDuration.setText("Duration: 0:0:0 / " + total_hour + ":" + total_minute+":" + total_second);

                });

                // Handle end of media event
                mediaPlayer.setOnEndOfMedia(() -> {

                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.stop();
                    isPlayed = false;

                    try
                    {
                        btnPlay.setGraphic(new ImageView(new Image(new FileInputStream("src/main/resources/Icon/Play.png"))));
                    }
                    catch (FileNotFoundException e)
                    {
                        throw new RuntimeException(e);
                    }

                });


                // Bind media view size to the application size
                Scene scene = MediaView.getScene();
                MediaView.fitWidthProperty().bind(scene.widthProperty());
                MediaView.fitHeightProperty().bind(scene.heightProperty());

                // Set initial volume based on slider value
                Volume.setValue(mediaPlayer.getVolume() * 100);
            }
        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }
    }


    // Method to handle the play/pause button click
    @FXML
    void btnPlay()
    {
        try
        {
            // Toggle between play and pause
            if(!isPlayed)
            {
                btnPlay.setGraphic(new ImageView(new Image(Files.newInputStream(Paths.get("src/main/resources/Icon/Pause.png")))));
                mediaPlayer.play();
                isPlayed = true;
                mediaPlayer.seek(Duration.seconds(slider.getValue()));
            }
            else
            {
                btnPlay.setGraphic(new ImageView(new Image(Files.newInputStream(Paths.get("src/main/resources/Icon/Play.png")))));
                mediaPlayer.pause();
                isPlayed = false;
            }
        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }
    }


    // Method to handle stopping the media
    @FXML
    void btnStop()
    {

        try
        {
            btnPlay.setGraphic(new ImageView(new Image(Files.newInputStream(Paths.get("src/main/resources/Icon/Play.png")))));
            mediaPlayer.stop();
            isPlayed = false;
        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }

    }


    // Method to handle slider functionality
    @FXML
    void sliderPressed()
    {

        try
        {
            mediaPlayer.seek(Duration.seconds(slider.getValue()));

        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }

    }


    // Method to handle forwarding the media
    @FXML
    void btnForward()
    {

        try
        {
            slider.setValue(slider.getValue()+5);
            mediaPlayer.seek(Duration.seconds(slider.getValue()));
        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }

    }

    @FXML
    void btnRewind()
    {

        try
        {
            slider.setValue(slider.getValue()-5);
            mediaPlayer.seek(Duration.seconds(slider.getValue()));
        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }

    }


    // Method to handle rewinding the media
    @FXML
    void volumePressed()
    {

        try
        {
            if (mediaPlayer != null)
            {

                Volume.valueProperty().addListener(observable ->
                        mediaPlayer.setVolume(Volume.getValue() / 100));
            }
        }
        catch (Exception ignored)
        {
            // Handle exceptions
        }

    }

}