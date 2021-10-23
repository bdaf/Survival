package pl.bdaf.gui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static pl.bdaf.person.GameEngine.PERSON_PASSES;
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
        engine.addObserver(PERSON_PASSES,this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aEvent) {

    }
}
