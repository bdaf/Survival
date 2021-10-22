package pl.bdaf.person;

import java.util.List;

import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;

public class GameEngine {

    private static final int MIN_EXPEDITION_DAYS = 1;
    private static final int MAX_EXPEDITION_DAYS = 3;
    private final Backpack backpack;
    private final GameQueue queue;
    private boolean endOfGame;
    private String dailyDescribe;
    private int day;

    public GameEngine(List<Person> aPersonList) {
        this(aPersonList, new Backpack());
    }

    public GameEngine(List<Person> aPersonList, Backpack aBackpack) {
        queue = new GameQueue(aPersonList);
        queue.addObserver(this);
        backpack = aBackpack;
        day = 1;
        dailyDescribe = getDescriptionOfAlivePeople();
    }

    void nextDay() {
        day++;
        StringBuilder currentDayDiary = new StringBuilder("Day " + day + "\n");
        currentDayDiary.append(getDescriptionOfAlivePeople());
        for (Person p : queue.getDeadPeople()) {
            currentDayDiary.append(DiaryWriter.describeDeadPerson(p));
        }
        dailyDescribe = currentDayDiary.toString();
    }

    private String getDescriptionOfAlivePeople() {
        StringBuilder currentDayDiary = new StringBuilder();
        for (Person p : queue.getAlivePeople()) {
            if(expeditionDayLeft(p) <= 0){
                p.setStrength(p.getStrength() + 1);
                p.setHydrationPoints(p.getHydrationPoints() - 1);
                p.setSatietyPoints(p.getHydrationPoints() - 1);
                p.setCheerfulness(p.getCheerfulness() - 1);
                currentDayDiary.append(DiaryWriter.describe(p));
            } else {
                currentDayDiary.append(DiaryWriter.describeExpeditionDay(p));
            }
        }
        return currentDayDiary.toString();
    }

    public void pass() {
        queue.next();
    }

    int getCurrentDay() {
        return day;
    }

    Person getActivePerson() {
        return queue.getActivePerson();
    }

    public boolean isEndOfGame() {
        return endOfGame;
    }

    void endOfGame() {
        endOfGame = true;
        //TODO
    }

    public void drink(){drink(1);}

    public void drink(int aAmountOfSupply) {
        if(reduceNumberOfSupply(aAmountOfSupply, WATER_BOTTLE)){
            getActivePerson().drink();
            return;
        }
        throw new IllegalStateException("There is no that many water bottles in backpack!");
    }

    public void eat(){eat(1);}

    public void eat(int aAmountOfSupply) {
        if(reduceNumberOfSupply(aAmountOfSupply, TOMATO_SOUP)){
            getActivePerson().eat();
            return;
        }
        throw new IllegalStateException("There is no that many tomato soups in backpack!");
    }

    void goForExpeditionAndPass(){ // from 1 day to 3 days on expedition
        getActivePerson().setExpeditionDaysLeft(getActivePerson().getState().getRand()
                .nextInt(MAX_EXPEDITION_DAYS-MIN_EXPEDITION_DAYS+1)+MIN_EXPEDITION_DAYS);
        pass();
    }

    public String getDailyDescribe() {
        return dailyDescribe;
    }

    void setDeadDayFor(List<Person> aPeopleJustDied) {
        for (Person person : aPeopleJustDied) {
            person.setDeadDay(day);
        }
    }

    private int expeditionDayLeft(Person aPerson) { // returns how many expedition days left
        int expeditionDaysLeft = aPerson.getExpeditionDaysLeft();
        if(expeditionDaysLeft == 1){ // he has just returned from expedition
            aPerson.goForExpedition(backpack); // do random action with expedition
        }
        aPerson.setExpeditionDaysLeft(aPerson.getExpeditionDaysLeft()-1);
        if(aPerson.getExpeditionDaysLeft()<=0){
            aPerson.setExpeditionDaysLeft(0);
        }
        return aPerson.getExpeditionDaysLeft();
    }


    public int getAmountOf(String aWaterBottle) {
        return backpack.getAmountOf(aWaterBottle);
    }

    // returns whether there was supply in backpack to remove
    private boolean reduceNumberOfSupply(int aAmountOfSupply, String aWaterBottle) {
        if (backpack.getAmountOf(aWaterBottle) >= aAmountOfSupply) {
            backpack.remove(aWaterBottle, aAmountOfSupply);
            return true;
        }
        return false;
    }
}
