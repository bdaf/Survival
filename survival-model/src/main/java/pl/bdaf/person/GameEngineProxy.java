package pl.bdaf.person;

import java.beans.PropertyChangeListener;
import java.util.List;

import static pl.bdaf.person.PersonStatistic.*;
import static pl.bdaf.person.PersonStatistic.BERTA;

public class GameEngineProxy implements GameEngineI {
    private static GameEngineProxy gameEngineProxy;
    private GameEngine engine;

    public static GameEngineProxy getInstance() {
        if (gameEngineProxy == null || gameEngineProxy.isEndOfGame())
            gameEngineProxy = new GameEngineProxy();
        return gameEngineProxy;
    }

    private GameEngineProxy() {
        engine = new GameEngine(List.of(new Person(TED), new Person(DOLORES), new Person(TIMMY), new Person(BERTA)));
    }

    private boolean canDoAnyAction() {
        return !gameEngineProxy.isEndOfGame();
    }

    @Override
    public void passWholeDay() {
        if (canDoAnyAction()) {
            engine.passWholeDay();
        }
    }

    @Override
    public void passToNextPerson() {
        if (canDoAnyAction()) {
            engine.passToNextPerson();
        }
    }

    @Override
    public int getAmountOf(String aResource) {
        return engine.getAmountOf(aResource);
    }

    @Override
    public void eat() {
        if (canDoAnyAction()) {
            engine.eat();
        }
    }

    @Override
    public void drink() {
        if (canDoAnyAction()) {
            engine.drink();
        }
    }


    @Override
    public void goForExpeditionAndPass() {
        if (canDoAnyAction()) {
            engine.goForExpeditionAndPass();
        }
    }

    @Override
    public void addObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        if (canDoAnyAction()) {
            engine.addObserver(aNameOfProperty, aListener);
        }
    }

    @Override
    public void removeObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        if (canDoAnyAction()) {
            engine.removeObserver(aNameOfProperty, aListener);
        }
    }

    @Override
    public void removeObserver(PropertyChangeListener aListener) {
        if (canDoAnyAction()) {
            engine.removeObserver(aListener);
        }
    }

    @Override
    public String getDailyDescribe() {
        return engine.getDailyDescribe();

    }

    @Override
    public int getCurrentDay() {
        return engine.getCurrentDay();
    }

    @Override
    public Person getActivePerson() {
        if (canDoAnyAction()) {
            return engine.getActivePerson();
        }
        return null;
    }

    @Override
    public List<Person> getDeadPeople() {
        return engine.getDeadPeople();
    }

    @Override
    public List<Person> getAlivePeople() {
        return engine.getAlivePeople();
    }

    private boolean isEndOfGame() {
        return engine.isEndOfGame();
    }
}
