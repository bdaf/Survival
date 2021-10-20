package pl.bdaf.person;

import java.util.*;

public class GameQueue {
    private final List<Person> peopleToDelete;
    private final List<Person> alivePeople;
    private final Queue<Person> queue;
    private Person activePerson;
    private Set<GameEngine> observers;

    GameQueue(List<Person> aPersonList) {
        observers = new HashSet<>();
        alivePeople = aPersonList;
        peopleToDelete = new LinkedList();
        queue = new LinkedList();
        init();
    }

    private void init() {
        for (Person alivePerson : alivePeople) {
            if(!alivePerson.isAlive()) peopleToDelete.add(alivePerson);
        }
        for (Person person : peopleToDelete) {
            alivePeople.remove(person);
        }
        queue.addAll(alivePeople);
        if(queue.isEmpty()){
            endOfGame();
        } else {
            notifyObservers();
            next();
        }
    }

    void next() {
        if(queue.isEmpty())
            init();
        else activePerson = queue.poll();
    }

    private void endOfGame() {
        observers.forEach(gm -> gm.endOfGame());
    }

    Person getActivePerson() {
        return activePerson;
    }

    private void addObserver(GameEngine aObserver){
        observers.add(aObserver);
    }

    private void removeObserver(GameEngine aObserver){
        observers.remove(aObserver);
    }

    private void notifyObservers(){
        observers.forEach(gm -> gm.nextDay());
    }
}
