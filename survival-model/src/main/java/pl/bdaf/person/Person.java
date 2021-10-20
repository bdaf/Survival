package pl.bdaf.person;

import static pl.bdaf.person.State.DEAD;

public class Person {

    public int strength;
    public int hydrationPoints;
    public int satietyPoints;

    public int cheerfulness;

    private State state;

    Person(PersonStatistic aStats){
        strength = aStats.strength;
        cheerfulness = aStats.cheerfulness;
        satietyPoints = aStats.satietyPoints;
        hydrationPoints = aStats.hydrationPoints;
        state = new State.Healthy();
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

    void goOutsideFindFood() {
        state.goOutsideFindFood(this);
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
        return !(getState().toString().equals(DEAD));
    }
}
