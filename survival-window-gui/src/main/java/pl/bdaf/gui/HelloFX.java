package pl.bdaf.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MusicInGame.MUSIC_IN_MENU.play();
        //ModeOfGame modeOfGame = chooseKindOfGame(); TODO get name

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("fxml/menu.fxml"));
        loader.setController(new MenuController());
        stage.getIcons().add(new Image("jpg/icon.jpg"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Survival");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}