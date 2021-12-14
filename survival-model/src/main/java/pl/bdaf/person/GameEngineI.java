package pl.bdaf.person;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface GameEngineI {

    // actions
    boolean passWholeDay();
    boolean passToNextPerson();
    boolean eat();
    boolean drink();
    boolean goForExpeditionAndPass();
    boolean addObserver(String aNameOfProperty, PropertyChangeListener aListener);
    boolean removeObserver(String aNameOfProperty, PropertyChangeListener aListener);
    boolean removeObserver(PropertyChangeListener aListener);

    // getters
    int getAmountOf(String aResource);
    String getDailyDescribe();
    int getCurrentDay();
    Person getActivePerson();
    List<Person> getDeadPeople();
    List<Person> getAlivePeople();
}
