package pl.bdaf.person;

import java.beans.PropertyChangeListener;

public interface GameEngineI {

    void passWholeDay();
    void pass();
    void eat();
    void drink();
    void goForExpedition();
    void goForExpeditionAndPass();
    void addObserver(String aNameOfProperty, PropertyChangeListener aListener);
    void removeObserver(String aNameOfProperty, PropertyChangeListener aListener);
}
