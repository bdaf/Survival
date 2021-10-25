package pl.bdaf.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import static pl.bdaf.gui.MainWindow.getMenu;
import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.GameEngine.*;
import static pl.bdaf.person.PersonStatistic.*;

public class GameController implements PropertyChangeListener {

    private final GameEngine engine;
    private final MultiWindowTextGUI gui;
    private Panel containerPanel;
    private Panel rightPanel;
    private Panel mainPanel;
    private Panel leftPanel;
    private BasicWindow window;
    private Panel botLeftPanel;

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
        engine.addObserver(DAY_PASSES, this);
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
        preparingRightPanel();
        mainPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Daily diary")));
        containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay() + " - " + engine.getActivePerson().getName())));
        window.setComponent(containerPanel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
    }

    private void preparingRightPanel() {
        rightPanel = new Panel();
        new Label(engine.getDailyDescribe()).addTo(rightPanel);
        rightPanel.setPreferredSize(new TerminalSize(70, 20));
    }

    private void updateAmountsOfSupplies() {
        if (botLeftPanel == null) botLeftPanel = new Panel();
        botLeftPanel.removeAllComponents();
        botLeftPanel.addComponent(new Label("Water: " + engine.getAmountOf(WATER_BOTTLE)));
        botLeftPanel.addComponent(new Label("Tomato soup: " + engine.getAmountOf(TOMATO_SOUP)));
    }

    private void setActionsTo(ActionListBox aActionListBox) {
        aActionListBox.addItem("Drink", () -> engine.drink());
        aActionListBox.addItem("Eat", () -> engine.eat());
        aActionListBox.addItem("Expedition!", () -> engine.goForExpeditionAndPass());
        aActionListBox.addItem("Next person", () -> engine.pass());
        aActionListBox.addItem("Next day", () -> engine.passWholeDay());
        aActionListBox.addItem("Back to menu", () -> goBackToMenu());
    }

    @Override
    public void propertyChange(PropertyChangeEvent aEvent) {
        if (aEvent.getPropertyName().equals(END_OF_THE_GAME)) {
            MessageDialog.showMessageDialog(gui, "End Of The Game!", "Your score is " + engine.getCurrentDay() + " days!");
            goBackToMenu();
        } else if (aEvent.getPropertyName().equals(PERSON_PASSES)) {
            containerPanel.removeAllComponents();
            containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay() + " - " + engine.getActivePerson().getName())));
        }
        if (aEvent.getPropertyName().equals(DAY_PASSES) || aEvent.getPropertyName().contains(EATEN))  {
            rightPanel.removeAllComponents();
            new Label(engine.getDailyDescribe()).addTo(rightPanel);
        }
        if (aEvent.getPropertyName().contains(EATEN) || aEvent.getPropertyName().equals(RETURN_FROM_EXPEDITION)) {
            botLeftPanel.removeAllComponents();
            updateAmountsOfSupplies();
        }
        if(aEvent.getPropertyName().equals(SEND_MESSAGE)){
            MessageDialog.showMessageDialog(gui, aEvent.getOldValue().toString(), aEvent.getNewValue().toString());
        }
    }

    private void goBackToMenu() {
        MainWindow.getScreen().clear();
        window.close();
        gui.addWindowAndWait(getMenu());
    }

    private void preparingLeftPanel() {
        leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(14, 10));
        setActionsTo(actionListBox);
        Panel topLeftPanel = new Panel().addComponent(actionListBox);
        updateAmountsOfSupplies();
        leftPanel.addComponent(topLeftPanel.withBorder(Borders.singleLine("Actions")));
        leftPanel.addComponent(botLeftPanel.withBorder(Borders.singleLine("Supplies")));
    }
}
