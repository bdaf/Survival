package pl.bdaf.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.GameEngine.*;
import static pl.bdaf.person.PersonStatistic.*;

public class GameController implements PropertyChangeListener {

    private final GameEngine engine;
    private final MultiWindowTextGUI gui;
    private Panel rightPanel;

    public GameController(MultiWindowTextGUI aGui) {
        engine = new GameEngine(List.of(new Person(TED),new Person(DOLORES),new Person(TIMMY),new Person(BERTA)));
        gui = aGui;
        init();
    }

    private void init() {
        engine.addObserver(WATER_BOTTLE+EATEN,this);
        engine.addObserver(TOMATO_SOUP+EATEN,this);
        engine.addObserver(PERSON_PASSES,this);
        engine.addObserver(DAY_PASSES,this);
        engine.addObserver(END_OF_THE_GAME,this);
        makingPanels();
    }

    private void makingPanels() {
        BasicWindow window = new BasicWindow();

        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Panel leftPanel = new Panel();
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(14, 10));
        setActionsTo(actionListBox);
        leftPanel.addComponent(actionListBox);
        mainPanel.addComponent(leftPanel.withBorder(Borders.singleLine("Left Panel")));

        rightPanel = new Panel();
        new Label(engine.getDailyDescribe()).addTo(rightPanel);
        mainPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Right Panel")));

        window.setComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay())));
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        gui.addWindowAndWait(window);
    }

    private void setActionsTo(ActionListBox aActionListBox) {
        aActionListBox.addItem("Drink", () -> {
            engine.drink();
        });
        aActionListBox.addItem("Eat", () -> {
            engine.eat();
        });
        aActionListBox.addItem("Go on expedition!", () -> {
            engine.goForExpeditionAndPass();
        });
        aActionListBox.addItem("Next person!", () -> {
            engine.pass();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent aEvent) {
        rightPanel.removeAllComponents();
        new Label(engine.getDailyDescribe()).addTo(rightPanel);
    }
}
