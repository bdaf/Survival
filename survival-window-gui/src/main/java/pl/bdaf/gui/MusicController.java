package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MusicController {

    private final MusicInGame musicInGame;
    private final MusicInGame musicInMenu;

    @FXML private BorderPane borderPane;
    @FXML private Label topLabel;
    @FXML private Button botButton;

    MusicController(MusicInGame aMusicInGame, MusicInGame aMusicInMenu) {
        musicInGame = aMusicInGame;
        musicInMenu = aMusicInMenu;
    }

    @FXML
    private void initialize(){
        topLabel.setText("Set volume of music");
        borderPane.setCenter(createSlider());
        botButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) botButton.getScene().getWindow();
            stage.close();
        });
    }

    private Slider createSlider() {
        Slider slider = new Slider();
        slider.valueProperty().addListener((sliderInListener, aOld, aNew) -> {
            musicInGame.setVolume(aNew.doubleValue()/100);
            musicInMenu.setVolume(aNew.doubleValue()/100);
        });
        slider.setMin(0F);
        slider.setMax(100F);
        slider.setValue(musicInMenu.getVolume()*100);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(9);
        slider.setBlockIncrement(10);
        slider.setStyle("-fx-padding: 0 30 0 30;");
        return slider;
    }

    public static void showMusicSettingsWindow(Window aOwnerWindow) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MusicController.class.getClassLoader().getResource("fxml/musicOrHowToPlay.fxml"));
        loader.setController(new MusicController(MusicInGame.MUSIC_IN_GAME, MusicInGame.MUSIC_IN_MENU));
        Stage stage = new Stage();
        stage.getIcons().add(new Image("graphics/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.initOwner(aOwnerWindow);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Survival - setting music");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
