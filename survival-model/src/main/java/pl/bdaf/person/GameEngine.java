package pl.bdaf.person;

import java.util.List;

public class GameEngine {

    private final GameQueue queue;

    GameEngine(List<Person> aPersonList) {
        queue = new GameQueue(aPersonList);
    }

    void nextDay() {

    }
}
