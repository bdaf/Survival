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

import java.io.IOException;

import static javafx.application.Platform.exit;

public class MenuController {
    private final String nameOfPlayer;
    @FXML
    private Button quitButton;
    @FXML
    private Button aboutButton;
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
    void initialize(){
        startGameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> startGameClicked());
        howToPlayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> howToPlayClicked());
        aboutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> aboutClicked());
        quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> exit());

        helloPlayerLabel.setText("Hello "+nameOfPlayer);
    }

    private void startGameClicked(){
        MusicInGame.MUSIC_IN_MENU.stop();
        MusicInGame.MUSIC_IN_GAME.play();

        Stage stage = new Stage();
        stage.getIcons().add(new Image("graphics/icon.jpg"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/game.fxml"));
        loader.setController(new WindowGameController(stage));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        stage.setTitle("Survival - game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initOwner(quitButton.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(aWindowEvent -> {
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
        });
        stage.showAndWait();
    }

    private void howToPlayClicked() {
    }

    private void aboutClicked() {

    }
}
