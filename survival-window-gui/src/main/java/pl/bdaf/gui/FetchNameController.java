package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            launchWindowApp.setPlayerName(usernameTextField.getText());
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.close();
        });
    }
}
