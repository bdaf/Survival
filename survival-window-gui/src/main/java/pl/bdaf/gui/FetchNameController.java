package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static javafx.scene.input.KeyCode.ENTER;

public class FetchNameController {
    private final LaunchWindowApp launchWindowApp;

    @FXML
    private Button playButton;

    @FXML
    private TextField usernameTextField;

    public FetchNameController(LaunchWindowApp aLaunchWindowApp) {
        launchWindowApp = aLaunchWindowApp;
    }

    @FXML
    void initialize() {
        usernameTextField.setOnKeyPressed(event -> {
            if (event.getCode() == ENTER) setPlayerNameAndCloseWindow();
        });
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> setPlayerNameAndCloseWindow());
    }

    private void setPlayerNameAndCloseWindow() {
        launchWindowApp.setPlayerName(usernameTextField.getText());
        Stage stage = (Stage) playButton.getScene().getWindow();
        stage.close();
    }
}
