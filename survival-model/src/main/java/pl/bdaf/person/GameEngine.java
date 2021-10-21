package pl.bdaf.person;

import java.util.List;

public class GameEngine {

    private final Backpack backpack;
    private final GameQueue queue;
    private boolean endOfGame;
    private String dailyDescribe;
    private int day;

    GameEngine(List<Person> aPersonList) {
        this(aPersonList, new Backpack());
    }

    public GameEngine(List<Person> aPersonList, Backpack aBackpack) {
        queue = new GameQueue(aPersonList);
        queue.addObserver(this);
        backpack = aBackpack;
        day = 1;
    }

    void nextDay() {
        day++;
        StringBuilder currentDayDiary = new StringBuilder("Day " + day + "\n");
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
        for (Person p : queue.getDeadPeople()) {
            currentDayDiary.append(DiaryWriter.describeDeadPerson(p));
        }
        dailyDescribe = currentDayDiary.toString();
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

    void drink() {
        getActivePerson().drink();
    }

    void eat() {
        getActivePerson().eat();
    }

    void goForExpedition(){ // from 1 day to 3 days on expedition
        getActivePerson().setExpeditionDaysLeft(getActivePerson().getState().getRand().nextInt(3)+1);
    }

    String getDailyDescribe() {
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
}
