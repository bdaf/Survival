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
        playerName = showFetchNameWindow();
        showMenuWindow(stage, playerName);
    }

    private String showFetchNameWindow() throws Exception {
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

    static void showMenuWindow(Stage aMenuWindow, String aPlayerName) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LaunchWindowApp.class.getClassLoader().getResource("fxml/menu.fxml"));
        loader.setController(new MenuController(aPlayerName));
        aMenuWindow.getIcons().add(new Image("graphics/icon.jpg"));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        aMenuWindow.setTitle("Survival - menu");
        aMenuWindow.setResizable(false);
        aMenuWindow.setScene(scene);
        aMenuWindow.show();
    }

    static void showGameWindow(String aPlayerName){
        Stage gameWindow = new Stage();
        gameWindow.getIcons().add(new Image("graphics/icon.jpg"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LaunchWindowApp.class.getClassLoader().getResource("fxml/game.fxml"));
        loader.setController(new WindowGameController(gameWindow, aPlayerName));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        gameWindow.setTitle("Survival - game");
        gameWindow.setResizable(false);
        gameWindow.setScene(scene);
        gameWindow.setOnCloseRequest(aWindowEvent -> {
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
        });

        gameWindow.show();
    }

    void setPlayerName(String aPlayerName) {
        playerName = aPlayerName;
    }
}