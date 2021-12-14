package pl.bdaf.gui;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import pl.bdaf.person.GameEngineI;
import pl.bdaf.person.GameEngineProxy;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Platform.exit;
import static pl.bdaf.person.GameEngine.*;

public class WindowGameController implements PropertyChangeListener, Controller {
    private final Stage currentStage;
    private final GameEngineI engine;
    private final String playerName;
    private boolean isDayUpdatable;
    @FXML private VBox rightVBox;
    @FXML private Button nextDayButton;
    @FXML private Button nextPersonButton;
    @FXML private Button goOnExpeditionButton;
    @FXML private Button drinkButton;
    @FXML private Button eatButton;
    @FXML private Button menuButton;
    @FXML private Button exitButton;
    @FXML private Button diaryButton;
    @FXML private Label dayLabel;
    @FXML private Label bottlesOfWaterLabel;
    @FXML private Label cansOfSoupsLabel;
    @FXML private Label emptyToMoveArrowLabel;
    @FXML private ImageView arrowImageView;
    @FXML private ImageView tedImageView;
    @FXML private ImageView doloresImageView;
    @FXML private ImageView timmyImageView;
    @FXML private ImageView bertaImageView;
    @FXML private List<Node> buttonsInRightVBox;

    WindowGameController(Stage aStage, String aPlayerName) {
        playerName = aPlayerName;
        currentStage = aStage;
        isDayUpdatable = true;
        engine = GameEngineProxy.getInstance();
    }

    @FXML
    private void initialize() {
        // updating from other game
        setGuiToContinueGame();
        initButtons();
        buttonsInRightVBox = new ArrayList<>(rightVBox.getChildren());
        updateSuppliesViewsAndLabels();
        animateByScaleTransition(arrowImageView);
        engine.addObserver(UPDATE_SUPPLIES, this);
        engine.addObserver(PERSON_DID_ACTION_ABOUT_EXPEDITION, this);
        engine.addObserver(END_OF_THE_GAME, this);
        engine.addObserver(DAY_CHANGED, this);
        engine.addObserver(PERSON_PASSES, this);
        engine.addObserver(PEOPLE_DIE, this);

    }

    private void setGuiToContinueGame() {
        setArrowAboveProperCharacter(engine.getActivePerson());
        dayLabel.setText("Day " + engine.getCurrentDay());
        showThatPeopleDied(engine.getDeadPeople());
        List<Person> alivePeople = engine.getAlivePeople();
        for (Person p : alivePeople) {
            setPersonVisible(p,!p.isPersonOnExpedition());
        }
    }

    private void updateSuppliesViewsAndLabels() {
        int waterBottlesAmount = engine.getAmountOf(WATER_BOTTLE);
        int tomatoSoupsAmount = engine.getAmountOf(TOMATO_SOUP);
        bottlesOfWaterLabel.setText("Bottles of water: " + waterBottlesAmount);
        cansOfSoupsLabel.setText("Bottles of soups: " + tomatoSoupsAmount);

        rightVBox.getChildren().clear();
        rightVBox = new WaterImageSupplier().addSupplyImageToVBox(rightVBox, engine);
        rightVBox = new SoupImageSupplier().addSupplyImageToVBox(rightVBox, engine);
        rightVBox.getChildren().addAll(buttonsInRightVBox);
    }

    private void initButtons() {
        menuButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            engine.removeObserver(UPDATE_SUPPLIES, this);
            engine.removeObserver(PERSON_DID_ACTION_ABOUT_EXPEDITION, this);
            engine.removeObserver(END_OF_THE_GAME, this);
            engine.removeObserver(DAY_CHANGED, this);
            engine.removeObserver(PERSON_PASSES, this);
            engine.removeObserver(PEOPLE_DIE, this);
            MusicInGame.MUSIC_IN_GAME.stop();
            MusicInGame.MUSIC_IN_MENU.play();
            currentStage.close();
            LaunchWindowApp.showMenuWindow(new Stage(), playerName);
        });
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            currentStage.close();
            exit();
        });

        diaryButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            WindowBuilderFX stageBuilder = new WindowBuilderFX();
            stageBuilder.controller(new DiaryController(engine.getDailyDescribe(), engine.getCurrentDay()))
                    .title("Survival - diary")
                    .modality(Modality.APPLICATION_MODAL)
                    .owner(diaryButton.getScene().getWindow())
                    .viewName("diary.fxml");
            stageBuilder.build().showAndWait();
        });

        eatButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.eat());
        drinkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.drink());
        goOnExpeditionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.goForExpeditionAndPass());
        nextDayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.passWholeDay());
        nextPersonButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> engine.passToNextPerson());

    }

    @Override
    public void propertyChange(PropertyChangeEvent aPropertyChangeEvent) {
        if (aPropertyChangeEvent.getPropertyName().equals(PERSON_DID_ACTION_ABOUT_EXPEDITION)) {
            Person person = (Person) aPropertyChangeEvent.getNewValue();
            setPersonVisible(person, !person.isPersonOnExpedition());
        }
        if (aPropertyChangeEvent.getPropertyName().equals(DAY_CHANGED))
            dayLabel.setText("Day " + aPropertyChangeEvent.getNewValue());
        if (aPropertyChangeEvent.getPropertyName().equals(PERSON_PASSES)) {
            setArrowAboveProperCharacter((Person) aPropertyChangeEvent.getNewValue());
        } else if (aPropertyChangeEvent.getPropertyName().contains(UPDATE_SUPPLIES)) {
            updateSuppliesViewsAndLabels();
        } else if (aPropertyChangeEvent.getPropertyName().equals(PEOPLE_DIE)) {
            showThatPeopleDied((List<Person>) aPropertyChangeEvent.getNewValue());
        } else if (aPropertyChangeEvent.getPropertyName().equals(END_OF_THE_GAME)) {
            WindowBuilderFX stageBuilder = new WindowBuilderFX();
            stageBuilder.controller(new EndOfTheGameController((Integer) aPropertyChangeEvent.getNewValue()))
                    .title("Survival - end of the game")
                    .viewName("endOfGame.fxml")
                    .modality(Modality.APPLICATION_MODAL)
                    .owner(dayLabel.getScene().getWindow());
            stageBuilder.build().showAndWait();
            dayLabel.setText(dayLabel.getText()+"\nEnd of the game");
        }
    }

    private void showThatPeopleDied(List<Person> aDeadPeople) {
        for (int i = 0; i < aDeadPeople.size(); i++) {
            String name = aDeadPeople.get(i).getName();
            URL resources = getClass().getResource("/graphics/" + name + "Dead.png");
            switch (name) {
                case "Ted":
                    tedImageView.setImage(new Image(resources.toString()));
                    tedImageView.setRotate(90);
                    break;
                case "Dolores":
                    doloresImageView.setImage(new Image(resources.toString()));
                    doloresImageView.setRotate(270);
                    break;
                case "Timmy":
                    timmyImageView.setImage(new Image(resources.toString()));
                    timmyImageView.setRotate(270);
                    break;
                case "Berta":
                    bertaImageView.setImage(new Image(resources.toString()));
                    bertaImageView.setRotate(270);
                    break;
                default:
                    break;
            }
        }
    }

    private void setPersonVisible(Person aPerson, boolean aVisible) {
        if (aPerson.getName().equalsIgnoreCase(Person.Constants.TED)) tedImageView.setVisible(aVisible);
        else if (aPerson.getName().equalsIgnoreCase(Person.Constants.DOLORES)) doloresImageView.setVisible(aVisible);
        else if (aPerson.getName().equalsIgnoreCase(Person.Constants.TIMMY)) timmyImageView.setVisible(aVisible);
        else if (aPerson.getName().equalsIgnoreCase(Person.Constants.BERTA)) bertaImageView.setVisible(aVisible);
    }

    private void animateByScaleTransition(Node aNodeToScaleTransition) {
        ScaleTransition st = new ScaleTransition(Duration.millis(1000), aNodeToScaleTransition);
        st.setFromX(0.8);
        st.setFromY(0.8);
        st.setByX(0.3);
        st.setByY(0.3);
        st.setCycleCount(ScaleTransition.INDEFINITE);
        st.setAutoReverse(true);
        st.play();
    }

    private void setArrowAboveProperCharacter(Person aPerson) {
        String nameOfCharacter = aPerson.getName();
        switch (nameOfCharacter) {
            case Person.Constants.TED:
                emptyToMoveArrowLabel.setStyle("-fx-padding: 0 0 0 0;");
                break;
            case Person.Constants.DOLORES:
                emptyToMoveArrowLabel.setStyle("-fx-padding: 0 0 0 105;");
                break;
            case Person.Constants.TIMMY:
                emptyToMoveArrowLabel.setStyle("-fx-padding: 0 0 0 245;");
                break;
            case Person.Constants.BERTA:
                emptyToMoveArrowLabel.setStyle("-fx-padding: 0 0 0 340;");
                break;
            default:
                throw new IllegalStateException("Unexpected name of character value: " + nameOfCharacter);
        }
    }
}
