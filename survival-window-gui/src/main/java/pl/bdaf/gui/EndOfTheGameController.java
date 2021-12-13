package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EndOfTheGameController implements Controller {
    private final int finalDay;

    @FXML private Label endOfTheGameLabel;
    @FXML private Button exitButton;

    public EndOfTheGameController(int aFinalDay) {
        finalDay = aFinalDay;
    }

    @FXML private void initialize(){
        endOfTheGameLabel.setText(endOfTheGameLabel.getText()+" - Day "+ finalDay);
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }
}
