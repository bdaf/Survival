package pl.bdaf.gui;

import javafx.application.Application;
import javafx.stage.Stage;

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

    private String showFetchNameWindow() {
        WindowBuilderFX stageBuilder = new WindowBuilderFX();
        stageBuilder.controller(new FetchNameController(this))
                .viewName("fetchName.fxml")
                .title("Survival - choosing name");
        stageBuilder.build().showAndWait();

        if(playerName == null || playerName.equals("")) playerName = DEFAULT_NAME;
        return playerName;
    }

    static void showMenuWindow(Stage aStage, String aPlayerName) {
        WindowBuilderFX stageBuilder = new WindowBuilderFX();
        stageBuilder.controller(new MenuController(aPlayerName))
                .viewName("menu.fxml")
                .title("Survival - menu")
                .stage(aStage);
        stageBuilder.build().show();
    }

    static void showGameWindow(String aPlayerName){
        Stage gameWindow = new Stage();
        gameWindow.setOnCloseRequest(aWindowEvent -> {
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
        });

        WindowBuilderFX stageBuilder = new WindowBuilderFX();
        stageBuilder.controller(new WindowGameController(gameWindow, aPlayerName))
                .stage(gameWindow)
                .viewName("game.fxml")
                .title("Survival - game");
        stageBuilder.build().show();
    }

    void setPlayerName(String aPlayerName) {
        playerName = aPlayerName;
    }
}