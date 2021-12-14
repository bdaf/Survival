package pl.bdaf.person;

import java.beans.PropertyChangeListener;
import java.util.List;

import static pl.bdaf.person.PersonStatistic.*;
import static pl.bdaf.person.PersonStatistic.BERTA;

public class GameEngineProxy implements GameEngineI {
    private static GameEngineProxy gameEngineProxy;
    private final GameEngine engine;

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
    public boolean passWholeDay() {
        if (canDoAnyAction()) {
            return engine.passWholeDay();
        } return false;
    }

    @Override
    public boolean passToNextPerson() {
        if (canDoAnyAction()) {
            return engine.passToNextPerson();
        } return false;
    }

    @Override
    public boolean eat() {
        if (canDoAnyAction()) {
            return engine.eat();
        } return false;
    }

    @Override
    public boolean drink() {
        if (canDoAnyAction()) {
            return engine.drink();
        } return false;
    }

    @Override
    public boolean goForExpeditionAndPass() {
        if (canDoAnyAction()) {
            return engine.goForExpeditionAndPass();
        } return false;
    }

    @Override
    public boolean addObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        if (canDoAnyAction()) {
            return engine.addObserver(aNameOfProperty, aListener);
        } return false;
    }

    @Override
    public boolean removeObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        if (canDoAnyAction()) {
            return engine.removeObserver(aNameOfProperty, aListener);
        } return false;
    }

    @Override
    public boolean removeObserver(PropertyChangeListener aListener) {
        if (canDoAnyAction()) {
            return engine.removeObserver(aListener);
        } return false;
    }

    @Override
    public int getAmountOf(String aResource) {
        return engine.getAmountOf(aResource);
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
