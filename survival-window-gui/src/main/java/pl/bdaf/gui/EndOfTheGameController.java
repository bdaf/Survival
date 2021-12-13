package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

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

    static void showWindowAndWait(Window aWindow, int aCurrentDay) {

    }
}
