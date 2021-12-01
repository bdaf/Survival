package pl.bdaf.gui;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public enum MusicInGame {
    MUSIC_IN_MENU("audio/menuMusic.mp3",0.0),
    MUSIC_IN_GAME("audio/musicDuringGame.mp3",0.0);

    private MediaPlayer mediaPlayer;

    MusicInGame(String aNameOfMusic, double aVolume) {
        URL resources = getClass().getResource("/"+aNameOfMusic);
        mediaPlayer = new MediaPlayer(new Media(resources.toString()));
        setVolume(aVolume);
        mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
    }

    public void play() { mediaPlayer.play(); }

    public void pause() { if(mediaPlayer !=null) mediaPlayer.pause(); }

    public void stop() { if (mediaPlayer != null) mediaPlayer.stop(); }

    public void setVolume(double aValue) { if (mediaPlayer != null) mediaPlayer.setVolume(aValue); }
}