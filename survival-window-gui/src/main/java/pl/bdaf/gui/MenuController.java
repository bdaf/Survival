package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static javafx.application.Platform.exit;

public class MenuController implements Controller {
    private final String nameOfPlayer;

    @FXML
    private Button quitButton;
    @FXML
    private Button musicButton;
    @FXML
    private Button howToPlayButton;
    @FXML
    private Button startGameButton;
    @FXML
    private Label helloPlayerLabel;

    MenuController(String aNameOfPlayer) {
        nameOfPlayer = aNameOfPlayer;
    }

    @FXML
    void initialize() {
        startGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> startGameClicked());
        howToPlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> howToPlayClicked());
        musicButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> musicClicked());
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> exit());

        helloPlayerLabel.setText("Hello " + nameOfPlayer);
    }

    private void startGameClicked() {
        MusicInGame.MUSIC_IN_MENU.stop();
        MusicInGame.MUSIC_IN_GAME.play();
        Stage menuWindow = (Stage) quitButton.getScene().getWindow();
        menuWindow.close();
        LaunchWindowApp.showGameWindow(nameOfPlayer);
    }

    private void howToPlayClicked() {
        WindowBuilderFX stageBuilder = new WindowBuilderFX();
        stageBuilder.controller(new HowToPlayController())
                .title("Survival - how to play")
                .viewName("musicOrHowToPlay.fxml")
                .modality(Modality.APPLICATION_MODAL)
                .owner(quitButton.getScene().getWindow());
        stageBuilder.build().showAndWait();
    }

    private void musicClicked() {
        WindowBuilderFX stageBuilder = new WindowBuilderFX();
        stageBuilder.controller(new MusicController(MusicInGame.MUSIC_IN_GAME, MusicInGame.MUSIC_IN_MENU))
                .title("Survival - setting music")
                .viewName("musicOrHowToPlay.fxml")
                .modality(Modality.APPLICATION_MODAL)
                .owner(quitButton.getScene().getWindow());
        stageBuilder.build().showAndWait();
    }
}
