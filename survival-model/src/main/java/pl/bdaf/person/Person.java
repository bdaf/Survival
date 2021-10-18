package pl.bdaf.person;

public class Person {

    public int strength;
    private State state;

    Person(int aStrength) {
        strength = aStrength;
        state = new State.HealthyState(this);
    }

    public State getState() {
        return state;
    }

    public void setState(State aState) {
        state = aState;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
