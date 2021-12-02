package pl.bdaf.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static javafx.application.Platform.exit;
import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.GameEngine.*;
import static pl.bdaf.person.PersonStatistic.*;

public class WindowGameController implements PropertyChangeListener {
    private final Stage currentStage;
    private final GameEngine engine;
    private final String playerName;
    @FXML private Button nextDayButton;
    @FXML private Button nextPersonButton;
    @FXML private Button goOnExpeditionButton;
    @FXML private Button drinkButton;
    @FXML private Button eatButton;
    @FXML private Button menuButton;
    @FXML private Button exitButton;
    @FXML private Button diaryButton;
    @FXML private Label dayLabel;

    WindowGameController(Stage aStage, String aPlayerName) {
        playerName = aPlayerName;
        currentStage = aStage;
        engine = new GameEngine(List.of(new Person(TED), new Person(DOLORES), new Person(TIMMY), new Person(BERTA)));
    }

    @FXML
    private void initialize(){
        initButtons();

        engine.addObserver(WATER_BOTTLE + EATEN, this);
        engine.addObserver(TOMATO_SOUP + EATEN, this);
        engine.addObserver(RETURN_FROM_EXPEDITION, this);
        engine.addObserver(SEND_MESSAGE, this);
        engine.addObserver(END_OF_THE_GAME, this);
        engine.addObserver(PERSON_PASSES, this);
        engine.addObserver(UPDATE_DIARY, this);
    }

    private void initButtons() {
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
            currentStage.close();
            LaunchWindowApp.showMenuWindow(new Stage(), playerName);
        });
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            currentStage.close();
            exit();
        });
        diaryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e-> DiaryController.showDiaryAndWait(
                diaryButton, engine.getDailyDescribe(), engine.getCurrentDay()));
        eatButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.eat());
        drinkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.drink());
        goOnExpeditionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.goForExpeditionAndPass());
        eatButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.eat());
        nextDayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.passWholeDay());
        nextPersonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.pass());

    }


    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        if(aPropertyChangeEvent.getPropertyName().equals(PERSON_PASSES)){
            dayLabel.setText("Day " + engine.getCurrentDay());
        }
    }

}
