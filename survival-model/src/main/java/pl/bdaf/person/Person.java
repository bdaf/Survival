package pl.bdaf.person;

public class Person {

    public int strength;
    private State state;

    Person(int aStrength) {
        this(aStrength, new State.Healthy());
    }

    Person(int aStrength, State aState) {
        strength = aStrength;
        state = aState;
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

    public void eat() {
        state.eat(this);
    }

    public void drink() {
        state.drink(this);
    }

    public void setWorseState() {
        state = state.getWorseState();
    }

    public void setBetterState() {
        state = state.getBetterState();
    }

    public void goOutsideFindFood() {
        state.goOutsideFindFood(this);
    }
}
