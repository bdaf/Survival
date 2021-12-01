package pl.bdaf.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LaunchWindowApp extends Application {

    public static final String DEFAULT_NAME = "the one who is afraid to enter his or her name";
    private String playerName;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MusicInGame.MUSIC_IN_MENU.play();
        playerName = fetchNameStage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        loader.setController(new MenuController(playerName));
        stage.getIcons().add(new Image("graphics/icon.jpg"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Survival");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private String fetchNameStage() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/fetchName.fxml"));
        loader.setController(new FetchNameController(this));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("graphics/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Survival - choosing name");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
        if(playerName == null || playerName.equals("")) {
            playerName = DEFAULT_NAME;
        }
        return playerName;
    }

    void setPlayerName(String aPlayerName) {
        playerName = aPlayerName;
    }
}