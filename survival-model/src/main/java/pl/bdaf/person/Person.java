package pl.bdaf.person;

import static pl.bdaf.person.GameEngine.TOMATO_SOUP;
import static pl.bdaf.person.GameEngine.WATER_BOTTLE;
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

    void eat() {
        state.eat(this);
    }

    void drink() {
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
        return !getState().getName().equals(DEAD) && getHydrationPoints() > 0 && getSatietyPoints() > 0 && getCheerfulness() > 0;
    }

    public String getName() {
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

    public boolean isPersonOnExpedition(){
        if(expeditionDaysLeft > 0) return true;
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    void takeSupply(String aSupply, int aAmountOfSupply) {
        if(aAmountOfSupply > 0){
            if(aSupply.equalsIgnoreCase(WATER_BOTTLE)){
                drink();
            }
            else if(aSupply.equalsIgnoreCase(TOMATO_SOUP)){
                eat();
            } else {
                throw new IllegalArgumentException("Name of supply is neither water bottle or tomato soup.");
            }
            takeSupply(aSupply,aAmountOfSupply-1);
        }
    }

    public static class Constants {
        public static final String TED = "Ted";
        public static final String DOLORES = "Dolores";
        public static final String TIMMY = "Timmy";
        public static final String BERTA = "Berta";
    }
}
