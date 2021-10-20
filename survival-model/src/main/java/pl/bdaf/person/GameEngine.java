package pl.bdaf.person;

import java.util.List;

public class GameEngine {

    private final GameQueue queue;
    private int day;

    GameEngine(List<Person> aPersonList) {
        queue = new GameQueue(aPersonList);
    }

    void nextDay() {
        day++;
    }

    void endOfGame() {
        //TODO
    }
}
