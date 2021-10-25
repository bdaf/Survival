package pl.bdaf.person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;

public class GameEngine {

    private static final int MIN_EXPEDITION_DAYS = 1;
    private static final int MAX_EXPEDITION_DAYS = 3;
    public static final String END_OF_THE_GAME = "End Of the Game";
    public static final String PERSON_PASSES = "PERSON_PASSES";
    public static final String DAY_PASSES = "DAY_PASSES";
    public static final String EATEN = "EATEN";
    public static final String RETURN_FROM_EXPEDITION = "RETURN_FROM_EXPEDITION";
    public static final String SEND_MESSAGE = "SEND_MESSAGE";
    private final Backpack backpack;
    private final GameQueue queue;
    private String dailyDescribe;
    private int day;
    private PropertyChangeSupport observers;

    public GameEngine(List<Person> aPersonList) {
        this(aPersonList, new Backpack());
    }

    public GameEngine(List<Person> aPersonList, Backpack aBackpack) {
        queue = new GameQueue(aPersonList);
        queue.addObserver(this);
        backpack = aBackpack;
        day = 1;
        observers = new PropertyChangeSupport(this);
        dailyDescribe = getDescriptionOfAlivePeopleAndMakeDayChangesAbout(0);
    }

    public void pass() {
        Person oldPerson = getActivePerson();
        queue.next();
        notifyObservers(new PropertyChangeEvent(this, PERSON_PASSES, oldPerson, getActivePerson()));
    }

    public void drink() {
        drink(1);
    }

    public void drink(int aAmountOfSupply) {
        consumeSupply(aAmountOfSupply, WATER_BOTTLE);
    }

    public void eat() {
        eat(1);
    }

    public void eat(int aAmountOfSupply) {
        consumeSupply(aAmountOfSupply, TOMATO_SOUP);
    }

    public int getCurrentDay() {
        return day;
    }

    public Person getActivePerson() {
        return queue.getActivePerson();
    }

    public String getDailyDescribe() {
        return dailyDescribe;
    }

    public int getAmountOf(String aWaterBottle) {
        return backpack.getAmountOf(aWaterBottle);
    }

    void nextDay() {
        day++;
        StringBuilder currentDayDiary = new StringBuilder();
        currentDayDiary.append(getDescriptionOfAlivePeopleAndMakeDayChangesAbout(1));
        for (Person p : queue.getDeadPeople()) {
            currentDayDiary.append(DiaryWriter.describeDeadPerson(p));
        }
        dailyDescribe = currentDayDiary.toString();
        notifyObservers(new PropertyChangeEvent(this, DAY_PASSES, day - 1, day));
    }

    public void goForExpeditionAndPass() { // from 1 day to 3 days on expedition
        if(queue.getAlivePeople().stream().filter(p -> p.getExpeditionDaysLeft() > 0).count() > 0){
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Forbidden action", "Somebody else is on expedition!"));
        } else {
            getActivePerson().setExpeditionDaysLeft(getActivePerson().getState().getRand()
                    .nextInt(MAX_EXPEDITION_DAYS - MIN_EXPEDITION_DAYS + 1) + MIN_EXPEDITION_DAYS);
            pass();
        }
    }

    private String getDescriptionOfAlivePeopleAndMakeDayChangesAbout(int aFactorOfChanges) {
        StringBuilder currentDayDiary = new StringBuilder();
        for (Person p : queue.getAlivePeople()) {
            if (expeditionDayLeft(p, currentDayDiary) <= 0) {
                p.setStrength(p.getStrength() + aFactorOfChanges);
                p.setHydrationPoints(p.getHydrationPoints() - aFactorOfChanges);
                p.setSatietyPoints(p.getSatietyPoints() - aFactorOfChanges);
                p.setCheerfulness(p.getCheerfulness() - aFactorOfChanges);
                currentDayDiary.append(DiaryWriter.describe(p));
            } else {
                currentDayDiary.append(DiaryWriter.describeExpeditionDay(p));
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
            aCurrentDayDiary.append( aPerson + " came back from expedition with " + amountOfWater + " water bottles and " + amountOfSoup + " tomato soups.\n");
            notifyObservers(new PropertyChangeEvent(this, RETURN_FROM_EXPEDITION, oldBackpack, backpack));
        }
        aPerson.setExpeditionDaysLeft(aPerson.getExpeditionDaysLeft() - 1);
        if (aPerson.getExpeditionDaysLeft() <= 0) {
            aPerson.setExpeditionDaysLeft(0);
        }
        return aPerson.getExpeditionDaysLeft();
    }

    // returns whether there was supply in backpack to remove
    private boolean reduceNumberOfSupply(int aAmountOfSupply, String aWaterBottle) {
        if (backpack.getAmountOf(aWaterBottle) >= aAmountOfSupply) {
            backpack.remove(aWaterBottle, aAmountOfSupply);
            return true;
        }
        return false;
    }

    private void consumeSupply(int aAmountOfSupply, String aNameOfSupply) {
        if (aAmountOfSupply > 0 && reduceNumberOfSupply(aAmountOfSupply, aNameOfSupply)) {
            getActivePerson().takeSupply(aNameOfSupply, aAmountOfSupply);
            int numberOfSupply = backpack.getAmountOf(aNameOfSupply);
            dailyDescribe += getActivePerson().getName() + DiaryWriter.describeConsuming() + aNameOfSupply + ".\n";
            notifyObservers(new PropertyChangeEvent(this, aNameOfSupply + EATEN, numberOfSupply - aAmountOfSupply, numberOfSupply));
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Successfully consumed", getActivePerson().getName()+" consumed "+aNameOfSupply+"."));
            return;
        } else if (aAmountOfSupply <= 0) {
            throw new IllegalStateException("You can only take positive value of number of supply: " + aNameOfSupply);
        } else {
            notifyObservers(new PropertyChangeEvent(this, SEND_MESSAGE, "Lack of "+aNameOfSupply, "You don't have this supply to consume!"));
        }
    }

    void setDeadDayFor(List<Person> aPeopleJustDied) {
        for (Person person : aPeopleJustDied) {
            person.setDeadDay(day);
        }
    }

    public void addObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        observers.addPropertyChangeListener(aNameOfProperty, aListener);
    }

    public void removeObserver(String aNameOfProperty, PropertyChangeListener aListener) {
        observers.removePropertyChangeListener(aNameOfProperty, aListener);
    }

    public void notifyObservers(PropertyChangeEvent aPropertyChangeEvent) {
        observers.firePropertyChange(aPropertyChangeEvent);
    }

    void endOfGame() {
        String endOfGameDescribe = END_OF_THE_GAME + " - your score is " + getCurrentDay() + " day!";
        dailyDescribe += endOfGameDescribe;
        notifyObservers(new PropertyChangeEvent(this, END_OF_THE_GAME, null, endOfGameDescribe));
    }

    public void passWholeDay() {
        while (!isActiveCreatureLastInQueue()) {
            pass();
        }
        pass();
    }

    private boolean isActiveCreatureLastInQueue() {
        return queue.isActiveCreatureTheLast();
    }
}
