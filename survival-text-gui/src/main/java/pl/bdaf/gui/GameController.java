package pl.bdaf.gui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.GameEngine.*;
import static pl.bdaf.person.PersonStatistic.*;

public class GameController implements PropertyChangeListener {

    private final GameEngine engine;
    private final MultiWindowTextGUI gui;

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
    }

    @Override
    public void propertyChange(PropertyChangeEvent aEvent) {

    }
}
