package pl.bdaf.person;

import java.util.List;

public class GameEngine {

    private final GameQueue queue;
    private int day;
    private boolean endOfGame;

    public GameEngine(List<Person> aPersonList) {
        queue = new GameQueue(aPersonList);
        queue.addObserver(this);
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
