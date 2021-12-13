package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class HowToPlayController implements Controller {
    @FXML private BorderPane borderPane;
    @FXML private Label topLabel;
    @FXML private Button botButton;

    @FXML
    private void initialize(){
        topLabel.setText("How to play");
        topLabel.setPadding(new Insets(20,0,0,0));
        borderPane.setCenter(createText());
        botButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) botButton.getScene().getWindow();
            stage.close();
        });
    }

    private Text createText() {
        Text text = new Text("In game you have 4 people (Ted, Dolores, Timmy and Berta) " +
                "in shelter after atomic bomb explosion outside it. Your " +
                "mission is to send them on expedition, feed and water them " +
                "in a way that will make them alive as long as possible. " +
                "Diary will be your source of information. Have many days! \n\n" +
                "You will only need your mouse!");
        text.setWrappingWidth(450);
        text.setFont(new Font(18));
        text.setStyle("-fx-fill: #ffffff;");
        text.setTextAlignment(TextAlignment.JUSTIFY);
        return text;
    }

    public static void showHowToPlayWindow(Window aOwnerWindow) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MusicController.class.getClassLoader().getResource("fxml/musicOrHowToPlay.fxml"));
        loader.setController(new HowToPlayController());
        Stage stage = new Stage();
        stage.getIcons().add(new Image("graphics/icon.jpg"));
        Scene scene = new Scene(loader.load());
        stage.initOwner(aOwnerWindow);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Survival - how to play");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
