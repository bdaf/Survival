package pl.bdaf.person;

import static pl.bdaf.person.State.DEAD;

public class Person {

    private int expeditionDaysLeft;
    private int hydrationPoints;
    private int satietyPoints;
    private int cheerfulness;
    private int strength;
    private String name;
    private State state;
    private int deadDay;

    public Person(PersonStatistic aStats){
        state = new State.Healthy();
        expeditionDaysLeft = 0;
        name = aStats.getName();
        strength = aStats.strength;
        cheerfulness = aStats.cheerfulness;
        satietyPoints = aStats.satietyPoints;
        hydrationPoints = aStats.hydrationPoints;
    }

    public State getState() {
        return state;
    }

    void setState(State aState) {
        state = aState;
    }

    public int getStrength() {
        return strength;
    }

    void setStrength(int strength) {
        this.strength = strength;
    }

    public void eat() {
        state.eat(this);
    }

    public void drink() {
        state.drink(this);
    }

    void setWorseState() {
        state = state.getWorseState();
    }

    void setBetterState() {
        state = state.getBetterState();
    }

    void goForExpedition(Backpack aBackpack) {
        state.goForExpedition(this, aBackpack);
    }

    int getHydrationPoints() {
        return hydrationPoints;
    }

    void setHydrationPoints(int aHydrationPoints) {
        hydrationPoints = aHydrationPoints;
    }

    int getSatietyPoints() {
        return satietyPoints;
    }

    void setSatietyPoints(int aSatietyPoints) {
        satietyPoints = aSatietyPoints;
    }

    int getCheerfulness() {
        return cheerfulness;
    }

    void setCheerfulness(int aCheerfulness) {
        cheerfulness = aCheerfulness;
    }
    public boolean isAlive(){
        return !getState().toString().equals(DEAD) && getHydrationPoints() > 0 && getSatietyPoints() > 0 && getCheerfulness() > 0 ;
    }

    String getName() {
        return name;
    }

    int getDeadDay() {
        return deadDay;
    }

    void setDeadDay(int aDeadDay) {
        deadDay = aDeadDay;
    }

    int getExpeditionDaysLeft() {
        return expeditionDaysLeft;
    }

    void setExpeditionDaysLeft(int aExpeditionDaysLeft) {
        expeditionDaysLeft = aExpeditionDaysLeft;
    }

    @Override
    public String toString() {
        return name;
    }
}
