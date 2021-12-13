package pl.bdaf.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LaunchWindowApp extends Application {

    public static final String DEFAULT_NAME = "the one who is afraid to enter his or her name";
    private String playerName;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        playerName = showFetchNameWindow();
        MusicInGame.MUSIC_IN_MENU.play();
        showMenuWindow(stage, playerName);
    }

    static void showMenuWindow(Stage aStage, String aPlayerName) {
        new StageBuilderImpl().controller(new MenuController(aPlayerName))
                .viewName("menu.fxml")
                .title("Survival - menu")
                .stage(aStage)
                .build().show();
    }

    private String showFetchNameWindow() {
        new StageBuilderImpl().controller(new FetchNameController(this))
                .viewName("fetchName.fxml")
                .title("Survival - choosing name")
                .build().showAndWait();

        if(playerName == null || playerName.equals("")) playerName = DEFAULT_NAME;
        return playerName;
    }

    static void showGameWindow(String aPlayerName){
        Stage gameWindow = new Stage();
        gameWindow.setOnCloseRequest(aWindowEvent -> {
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
        });

        new StageBuilderImpl().controller(new WindowGameController(gameWindow, aPlayerName))
                .stage(gameWindow)
                .viewName("game.fxml")
                .title("Survival - game")
                .build().show();
    }

    void setPlayerName(String aPlayerName) {
        playerName = aPlayerName;
    }
}