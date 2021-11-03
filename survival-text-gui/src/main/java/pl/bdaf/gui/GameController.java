package pl.bdaf.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.GameEngine.*;
import static pl.bdaf.person.PersonStatistic.*;

public class GameController implements PropertyChangeListener {

    private final GameEngine engine;
    private final MultiWindowTextGUI gui;
    private Panel containerPanel;
    private Panel mainPanel;
    private Panel rightPanel;
    private Panel rightMidPanel;
    private Panel midPanel;
    private Panel leftPanel;
    private Panel midLeftPanel;
    private Panel bottomLeftPanel;
    private BasicWindow window;

    public GameController(MultiWindowTextGUI aGui) {
        engine = new GameEngine(List.of(new Person(TED), new Person(DOLORES), new Person(TIMMY), new Person(BERTA)));
        gui = aGui;
        init();
    }

    private void init() {
        engine.addObserver(WATER_BOTTLE + EATEN, this);
        engine.addObserver(TOMATO_SOUP + EATEN, this);
        engine.addObserver(RETURN_FROM_EXPEDITION, this);
        engine.addObserver(SEND_MESSAGE, this);
        engine.addObserver(END_OF_THE_GAME, this);
        engine.addObserver(PERSON_PASSES, this);
        engine.addObserver(UPDATE_DIARY, this);
        makingPanels();
    }

    private void makingPanels() {
        window = new BasicWindow();
        window.setTitle("Survival");
        containerPanel = new Panel();
        mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        preparingPanels();
        gui.addWindowAndWait(window);
    }

    private void preparingPanels() {
        preparingLeftPanel();
        mainPanel.addComponent(leftPanel);
        preparingMidPanel();
        mainPanel.addComponent(midPanel.withBorder(Borders.singleLine("Daily diary")));
        preparingRightPanel();
        mainPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Current person")));
        containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay() + " - " + engine.getActivePerson().getName())));
        window.setComponent(containerPanel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
    }

    private void preparingRightPanel() {
        rightPanel = new Panel();
        rightPanel.setPreferredSize(new TerminalSize(25, 15));
        updateAnimatedPerson(engine.getActivePerson().getName());
    }

    private void updateAnimatedPerson(String aName) {
        if (rightMidPanel == null) rightMidPanel = new Panel();
        rightMidPanel.removeAllComponents();
        rightMidPanel.addComponent(AnimateGenerator.getPersonAnimation(engine.getActivePerson().getName()));
        rightMidPanel.addTo(rightPanel);
    }

    private void setActionsTo(ActionListBox aActionListBox) {
        aActionListBox.addItem("Drink", () -> engine.drink());
        aActionListBox.addItem("Eat", () -> engine.eat());
        aActionListBox.addItem("Expedition!", () -> engine.goForExpeditionAndPass());
        aActionListBox.addItem("Next person", () -> engine.pass());
        aActionListBox.addItem("Next day", () -> engine.passWholeDay());
        aActionListBox.addItem("Clear diary", () -> engine.clearDiaryDescribe());
        aActionListBox.addItem("Back to menu", () -> goBackToMenu());
    }

    private void setDiaryDescribe(String aDailyDescribe) {
        midPanel.removeAllComponents();
        new Label(aDailyDescribe).addTo(midPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aEvent) {
        if (aEvent.getPropertyName().equals(END_OF_THE_GAME)) {
            setDiaryDescribe(engine.getDailyDescribe());
            AnimateGenerator.getEndOfGameAnimation(gui, engine.getCurrentDay());
            goBackToMenu();
        } else if (aEvent.getPropertyName().equals(PERSON_PASSES)) {
            containerPanel.removeAllComponents();
            containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay() + " - " + engine.getActivePerson().getName())));
            updateCurrentPersonAndDay();
            updateAnimatedPerson(engine.getActivePerson().getName());
        } else if (aEvent.getPropertyName().equals(UPDATE_DIARY)) {
            setDiaryDescribe(engine.getDailyDescribe());
        } else if (aEvent.getPropertyName().contains(EATEN) || aEvent.getPropertyName().equals(RETURN_FROM_EXPEDITION)) {
            updateAmountsOfSupplies();
        } else if (aEvent.getPropertyName().equals(SEND_MESSAGE)) {
            MessageDialog.showMessageDialog(gui, aEvent.getOldValue().toString(), aEvent.getNewValue().toString());
        }
    }

    private void goBackToMenu() {
        window.close();
    }

    private void updateAmountsOfSupplies() {
        if (midLeftPanel == null) midLeftPanel = new Panel();
        midLeftPanel.removeAllComponents();
        midLeftPanel.addComponent(new Label("Water: " + engine.getAmountOf(WATER_BOTTLE)).setForegroundColor(AnimateGenerator.getColorAccordingToNumber(engine.getAmountOf(WATER_BOTTLE))));
        midLeftPanel.addComponent(new Label("Soups: " + engine.getAmountOf(TOMATO_SOUP)).setForegroundColor(AnimateGenerator.getColorAccordingToNumber(engine.getAmountOf(TOMATO_SOUP))));
    }

    private void preparingLeftPanel() {
        leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(14, 7));
        setActionsTo(actionListBox);
        Panel topLeftPanel = new Panel().addComponent(actionListBox);
        updateAmountsOfSupplies();
        updateCurrentPersonAndDay();
        midLeftPanel.setPreferredSize(new TerminalSize(15, 2));
        bottomLeftPanel.setPreferredSize(new TerminalSize(15, 2));
        leftPanel.addComponent(topLeftPanel.withBorder(Borders.singleLine("Actions")));
        leftPanel.addComponent(midLeftPanel.withBorder(Borders.singleLine("Supplies")));
        leftPanel.addComponent(bottomLeftPanel.withBorder(Borders.singleLine("Currently")));
    }

    private void updateCurrentPersonAndDay() {
        if (bottomLeftPanel == null) bottomLeftPanel = new Panel();
        bottomLeftPanel.removeAllComponents();
        bottomLeftPanel.addComponent(new Label("Person: " + engine.getActivePerson().getName()));
        bottomLeftPanel.addComponent(new Label("Day: " + engine.getCurrentDay()));

    }

    private void preparingMidPanel() {
        midPanel = new Panel();
        new Label(engine.getDailyDescribe()).addTo(midPanel);
        midPanel.setPreferredSize(new TerminalSize(50, 15));
    }

}
