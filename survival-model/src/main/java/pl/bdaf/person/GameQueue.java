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
        next();
    }

    private void next() {
        if(queue.isEmpty())
            init();
        else activePerson = queue.poll();
    }

    void addObserver(GameEngine aObserver){
        observers.add(aObserver);
    }

    void removeObserver(GameEngine aObserver){
        observers.remove(aObserver);
    }

    void notifyObservers(){
        observers.forEach(gm -> gm.nextDay());
    }
}
