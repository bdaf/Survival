package pl.bdaf.person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class GameEngine implements GameEngineI {
    private static GameEngine gameEngine;
    public static final String TOMATO_SOUP = "tomato_soup";
    public static final String WATER_BOTTLE = "water_bottle";
    private static final int MIN_EXPEDITION_DAYS = 1;
    private static final int MAX_EXPEDITION_DAYS = 3;
    public static final String END_OF_THE_GAME = "End Of the Game";
    public static final String PERSON_DID_ACTION_ABOUT_EXPEDITION = "PERSON_DID_ACTION_ABOUT_EXPEDITION";
    public static final String PERSON_PASSES = "PERSON_PASSES";
    public static final String UPDATE_SUPPLIES = "UPDATE_SUPPLIES";
    public static final String DAY_CHANGED = "DAY_CHANGED";
    public static final String SEND_MESSAGE = "SEND_MESSAGE";
    public static final String PEOPLE_DIE = "PEOPLE_DIE";
    private final DiaryWriter diaryWriter;
    private final Backpack backpack;
    private final GameQueue queue;
    private String dailyDescribe;
    private int day;
    private PropertyChangeSupport observers;
    private boolean endOfGame;

    // for gameEngineProxy
    GameEngine(List<Person> aPersonList) {
        this(aPersonList, new Backpack());
    }

    // for tests
    GameEngine(List<Person> aPersonList, Backpack aBackpack) {
        queue = new GameQueue(aPersonList);
        queue.addObserver(this);
        backpack = aBackpack;
        day = 1;
        observers = new PropertyChangeSupport(this);
        diaryWriter = new DiaryWriter();
        dailyDescribe = getDescriptionOfAlivePeopleAndMakeDayChangesAbout(0);
    }

    public boolean passToNextPerson() {
        Person oldPerson = getActivePerson();
        int oldDay = getCurrentDay();
        boolean itWillBeNextDay = isActiveCreatureLastInQueue();
        queue.next();
        notifyObservers(new PropertyChangeEvent(this, PERSON_PASSES, oldPerson, getActivePerson()));
        if (itWillBeNextDay) notifyObservers(new PropertyChangeEvent(this, DAY_CHANGED, oldDay, getCurrentDay()));
        return true;
    }

    public boolean drink() {
        return drink(1);
    }

    public boolean drink(int aAmountOfSupply) {
        return consumeSupply(aAmountOfSupply, WATER_BOTTLE);
    }

    public boolean eat() {
        return eat(1);
    }

    public boolean eat(int aAmountOfSupply) { return consumeSupply(aAmountOfSupply, TOMATO_SOUP); }

    public int getCurrentDay() {
        return day;
    }

    public Person getActivePerson() {
        return queue.getActivePerson();
    }

    public String getDailyDescribe() {
        return dailyDescribe;
    }

    public int getAmountOf(String aResource) {
        return backpack.getAmountOf(aResource);
    }

    void nextDay() {
        day++;
        StringBuilder currentDayDiary = new StringBuilder();
        currentDayDiary.append(getDescriptionOfAlivePeopleAndMakeDayChangesAbout(1));
        for (Person p : queue.getDeadPeople()) {
            currentDayDiary.append(diaryWriter.describeDeadPerson(p));
            currentDayDiary.append("_");
        }
        dailyDescribe = currentDayDiary.toString();
    }

    public boolean goForExpeditionAndPass() { // from 1 day to 3 days on expedition
        if (queue.getAlivePeople().stream().filter(p -> p.getExpeditionDaysLeft() > 0).count() > 0) {
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Forbidden action", "Somebody else is on expedition!"));
        } else if (queue.getAlivePeople().size() <= 1) {
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Forbidden action", "You cannot let shelter be empty!"));
        } else {
            getActivePerson().setExpeditionDaysLeft(getActivePerson().getState().getRand()
                    .nextInt(MAX_EXPEDITION_DAYS - MIN_EXPEDITION_DAYS + 1) + MIN_EXPEDITION_DAYS);
            notifyObservers(new PropertyChangeEvent(this, PERSON_DID_ACTION_ABOUT_EXPEDITION, null, getActivePerson()));
            passToNextPerson();
            return true;
        }
        return false;
    }

    private String getDescriptionOfAlivePeopleAndMakeDayChangesAbout(int aFactorOfChanges) {
        StringBuilder currentDayDiary = new StringBuilder();
        for (Person p : queue.getAlivePeople()) {
            if (expeditionDayLeft(p, currentDayDiary) <= 0) {
                p.setStrength(p.getStrength() + aFactorOfChanges);
                p.setHydrationPoints(p.getHydrationPoints() - aFactorOfChanges);
                p.setSatietyPoints(p.getSatietyPoints() - aFactorOfChanges);
                p.setCheerfulness(p.getCheerfulness() - aFactorOfChanges);
                currentDayDiary.append(diaryWriter.describe(p));
                currentDayDiary.append("_");
            } else {
                currentDayDiary.append(diaryWriter.describeExpeditionDay(p));
            }
        }
        return currentDayDiary.toString();
    }

    private int expeditionDayLeft(Person aPerson, StringBuilder aCurrentDayDiary) { // returns how many expedition days left
        int expeditionDaysLeft = aPerson.getExpeditionDaysLeft();
        if (expeditionDaysLeft == 1) { // he has just returned from expedition
            Backpack oldBackpack = new Backpack(backpack);
            aPerson.goForExpedition(backpack); // do random action with expedition
            int amountOfWater = backpack.getAmountOf(WATER_BOTTLE) - oldBackpack.getAmountOf(WATER_BOTTLE);
            int amountOfSoup = backpack.getAmountOf(TOMATO_SOUP) - oldBackpack.getAmountOf(TOMATO_SOUP);
            aCurrentDayDiary.append(aPerson + " came back from expedition with " + amountOfWater + " water bottles and " + amountOfSoup + " tomato soups.\n");
            notifyObservers(new PropertyChangeEvent(this, UPDATE_SUPPLIES, null, null));
        }
        aPerson.setExpeditionDaysLeft(aPerson.getExpeditionDaysLeft() - 1);
        if (aPerson.getExpeditionDaysLeft() <= 0) {
            aPerson.setExpeditionDaysLeft(0);
            notifyObservers(new PropertyChangeEvent(this, PERSON_DID_ACTION_ABOUT_EXPEDITION, null, aPerson));
        }
        return aPerson.getExpeditionDaysLeft();
    }

    // returns whether there was supply in backpack to remove
    private boolean reduceNumberOfSupply(int aAmountOfSupply, String aWaterBottle) {
        if (backpack.getAmountOf(aWaterBottle) >= aAmountOfSupply) {
            backpack.removeSupply(aWaterBottle, aAmountOfSupply);
            return true;
        }
        return false;
    }

    private boolean consumeSupply(int aAmountOfSupply, String aNameOfSupply) {
        if (aAmountOfSupply > 0 && reduceNumberOfSupply(aAmountOfSupply, aNameOfSupply)) {
            Backpack oldBackpack = new Backpack(backpack);
            getActivePerson().takeSupply(aNameOfSupply, aAmountOfSupply);
            notifyObservers(new PropertyChangeEvent(this, UPDATE_SUPPLIES, oldBackpack, backpack));
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Successfully consumed", getActivePerson().getName() + " consumed " + aNameOfSupply + "."));
            return true;
        } else if (aAmountOfSupply <= 0) {
            throw new IllegalStateException("You can only take positive value of number of supply: " + aNameOfSupply);
        } else {
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Lack of " + aNameOfSupply, "You don't have this supply to consume!"));
        }
        return false;
    }

    void setDeadDayFor(List<Person> aPeopleJustDied) {
        for (Person person : aPeopleJustDied) {
            person.setDeadDay(day);
        }
        notifyObservers(new PropertyChangeEvent(this, PEOPLE_DIE, null, aPeopleJustDied));
    }

    public List<Person> getDeadPeople() {
        return queue.getDeadPeople();
    }

    public List<Person> getAlivePeople() {
        return queue.getAlivePeople();
    }

    public boolean addObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        if (!observers.hasListeners(aNameOfProperty)) {
            observers.addPropertyChangeListener(aNameOfProperty, aListener);
            return true;
        }
        return false;
    }

    public boolean removeObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        observers.removePropertyChangeListener(aNameOfProperty, aListener);
        return true;
    }

    public boolean removeObserver(PropertyChangeListener aListener) {
        observers.removePropertyChangeListener(aListener);
        return true;
    }

    private boolean notifyObservers(PropertyChangeEvent aPropertyChangeEvent) {
        observers.firePropertyChange(aPropertyChangeEvent);
        return true;
    }

    void endOfGame() {
        String endOfGameDescribe = END_OF_THE_GAME + " - your score is " + (getCurrentDay()) + " day!";
        dailyDescribe += endOfGameDescribe;
        endOfGame = true;
        notifyObservers(new PropertyChangeEvent(this, END_OF_THE_GAME, null, getCurrentDay()));
    }

    public boolean passWholeDay() {
        while (!isActiveCreatureLastInQueue()) {
            passToNextPerson();
        }
        passToNextPerson();
        return true;
    }

    private boolean isActiveCreatureLastInQueue() {
        return queue.isActiveCreatureTheLast();
    }

    boolean isEndOfGame() {
        return endOfGame;
    }
}
