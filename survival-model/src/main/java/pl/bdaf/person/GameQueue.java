package pl.bdaf.person;

import java.util.*;

class GameQueue {
    private final List<Person> peopleToDelete;
    private final Set<GameEngine> observers;
    private final List<Person> alivePeople;
    private final Queue<Person> queue;
    private Person activePerson;

    GameQueue(List<Person> aPersonList) {
        observers = new HashSet<>();
        alivePeople = aPersonList;
        peopleToDelete = new LinkedList();
        queue = new LinkedList();
        init();
    }

    private void init() {
        makeStarvingDead();
        queue.addAll(alivePeople);
        if (queue.isEmpty()) {
            endOfGame();
        } else { // make new day
            notifyObservers();
            next();
        }
    }

    void next() {
        if (queue.isEmpty())
            init();
        else activePerson = queue.poll();
    }

    private void makeStarvingDead() {
        for (Person alivePerson : alivePeople) {
            if(alivePerson.getHydrationPoints() < 0 || alivePerson.getSatietyPoints() < 0 || !alivePerson.isAlive()){
                alivePerson.setState(new State.Dead());
                peopleToDelete.add(alivePerson);
            }
        }
        for (Person person : peopleToDelete) {
            alivePeople.remove(person);
        }
        peopleToDelete.clear();
    }

    private void endOfGame() {
        observers.forEach(gm -> gm.endOfGame());
    }

    Person getActivePerson() {
        return activePerson;
    }

    void addObserver(GameEngine aObserver) {
        observers.add(aObserver);
    }

    void removeObserver(GameEngine aObserver) {
        observers.remove(aObserver);
    }

    void notifyObservers() {
        observers.forEach(gm -> gm.nextDay());
    }
}
