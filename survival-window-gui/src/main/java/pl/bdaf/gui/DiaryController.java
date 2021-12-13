package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DiaryController implements Controller {

    private final String diaryContent;
    private final int day;
    @FXML private Button closeDiaryButton;
    @FXML private Text firstPageText;
    @FXML private Text secondPageText;

    DiaryController(String aDiaryContent, int aDay) {
        diaryContent = aDiaryContent;
        day = aDay;
    }

    @FXML
    private void initialize(){
        closeDiaryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Stage stage = (Stage) closeDiaryButton.getScene().getWindow();
            stage.close();
        });
        splitTextOnTwoPagesAndSetTextInThesePages();
    }

    private void splitTextOnTwoPagesAndSetTextInThesePages() {
        String[] partsOfDiaryContent = diaryContent.split("_");
        firstPageText.setText("Day "+day+"\n");
        secondPageText.setText("");
        for (int i = 0; i < partsOfDiaryContent.length; i++) {
            if(i%2 == 0) firstPageText.setText(firstPageText.getText()+partsOfDiaryContent[i]);
            else secondPageText.setText(secondPageText.getText()+partsOfDiaryContent[i]);
        }
    }
}
