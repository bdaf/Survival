package pl.bdaf.person;

import java.util.List;

public class GameEngine {

    private final Backpack backpack;
    private final GameQueue queue;
    private boolean endOfGame;
    private int day;

    GameEngine(List<Person> aPersonList) {
        this(aPersonList,new Backpack());
    }

    public GameEngine(List<Person> aPersonList, Backpack aBackpack) {
        queue = new GameQueue(aPersonList);
        queue.addObserver(this);
        backpack = aBackpack;
        day = 1;
    }

    void nextDay() {
        day++;
    }

    public void pass() {
        queue.next();
    }

    int getCurrentDay() {
        return day;
    }

    Person getActivePerson() {
        return queue.getActivePerson();
    }

    public boolean isEndOfGame() {
        return endOfGame;
    }

    void endOfGame() {
        endOfGame = true;
        //TODO
    }
}
