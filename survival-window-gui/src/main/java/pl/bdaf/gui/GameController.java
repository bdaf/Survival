package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GameController {
    private final Stage currentStage;

    @FXML
    private Button exitButton;

    GameController(Stage aStage) {
        currentStage = aStage;
    }

    @FXML
    private void initialize(){
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            currentStage.close();
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
        });
    }
}
