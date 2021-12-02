package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DiaryController {

    private final String diaryContent;
    private final int day;
    @FXML private Button closeDiaryButton;
    @FXML private Text firstPageText;
    @FXML private Text secondPageText;

    @FXML
    private void initialize(){
        closeDiaryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) closeDiaryButton.getScene().getWindow();
            stage.close();
        });

        String[] partsOfDiaryContent = diaryContent.split("_");
        firstPageText.setText("Day "+day+"\n");
        secondPageText.setText("");
        for (int i = 0; i < partsOfDiaryContent.length; i++) {
            if(i%2 == 0) firstPageText.setText(firstPageText.getText()+partsOfDiaryContent[i]);
            else secondPageText.setText(secondPageText.getText()+partsOfDiaryContent[i]);
        }
    }

    DiaryController(String aDiaryContent, int aDay) {
        diaryContent = aDiaryContent;
        day = aDay;
    }

    static void showDiaryAndWait(Button aDiaryButton, String aDiaryContent, int aDay) {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("graphics/icon.jpg"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DiaryController.class.getClassLoader().getResource("fxml/diary.fxml"));
        loader.setController(new DiaryController(aDiaryContent, aDay));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        stage.setTitle("Survival - diary");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initOwner(aDiaryButton.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
