package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
        try {
            HowToPlayController.showHowToPlayWindow(quitButton.getScene().getWindow());
        } catch (Exception aE) {
            aE.printStackTrace();
        }
    }

    private void musicClicked() {
        try {
            MusicController.showMusicSettingsWindow(quitButton.getScene().getWindow());
        } catch (Exception aE) {
            aE.printStackTrace();
        }
    }
}
