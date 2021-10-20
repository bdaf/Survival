package pl.bdaf.person;

import java.util.*;

class GameQueue {
    private final List<Person> peopleToDelete;
    private final Set<GameEngine> observers;
    private final List<Person> alivePeople;
    private final List<Person> deadPeople;
    private final Queue<Person> queue;
    private Person activePerson;

    GameQueue(List<Person> aPersonList) {
        observers = new HashSet<>();
        alivePeople = aPersonList;
        deadPeople = new LinkedList();
        peopleToDelete = new LinkedList();
        queue = new LinkedList();
        init();
    }

    private void init() {
        notifyObservers();
        makeStarvingDead();
        queue.addAll(alivePeople);
        if (queue.isEmpty()) {
            endOfGame();
        } else { // make new day
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
            if(!alivePerson.isAlive()){
                alivePerson.setState(new State.Dead());
                peopleToDelete.add(alivePerson);
            }
        }
        observers.forEach(gm -> gm.setDeadDayFor(peopleToDelete));
        for (Person person : peopleToDelete) {
            deadPeople.add(person);
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
        observers.forEach(GameEngine::nextDay);
    }

    List<Person> getAlivePeople() {
        return List.copyOf(alivePeople);
    }

    List<Person> getDeadPeople() {
        return List.copyOf(deadPeople);
    }
}
