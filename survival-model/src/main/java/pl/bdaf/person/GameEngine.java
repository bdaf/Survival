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
            p.setStrength(p.getStrength() + 1);
            p.setHydrationPoints(p.getHydrationPoints() - 1);
            p.setSatietyPoints(p.getHydrationPoints() - 1);
            p.setCheerfulness(p.getCheerfulness() - 1);
            currentDayDiary.append(DiaryWriter.describe(p));
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

    void setDeadDayFor(List<Person> aPeopleJustDied) {
        for (Person person : aPeopleJustDied) {
            person.setDeadDay(day);
        }
    }

    void drink() {
        getActivePerson().drink();
    }

    void eat() {
        getActivePerson().eat();
    }

    void goForExpedition(){
        getActivePerson().goForExpedition(backpack);
    }

    String getDailyDescribe() {
        return dailyDescribe;
    }
}
