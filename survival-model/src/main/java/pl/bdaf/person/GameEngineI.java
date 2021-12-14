package pl.bdaf.person;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface GameEngineI {

    void passWholeDay();
    void passToNextPerson();
    int getAmountOf(String aResource);
    void eat();
    void drink();
    void goForExpeditionAndPass();
    void addObserver(String aNameOfProperty, PropertyChangeListener aListener);
    void removeObserver(String aNameOfProperty, PropertyChangeListener aListener);
    void removeObserver(PropertyChangeListener aListener);
    String getDailyDescribe();
    int getCurrentDay();
    Person getActivePerson();
    List<Person> getDeadPeople();
    List<Person> getAlivePeople();
}
