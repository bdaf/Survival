package pl.bdaf.person;

import java.util.Random;

public abstract class State {

    public static final String HEALTHY = "Healthy";
    public static final String SICK = "Sick";
    public static final String DEAD = "Dead";
    public static final String MEDIUM = "Medium";

    Random rand;
    private String name;

    protected State() {
        this(new Random());
    }

    protected State(Random aRandom) {
        rand = aRandom;
    }

    abstract void goOutsideFindFood(Person aPerson);

    abstract void drink(Person aPerson);

    abstract void eat(Person aPerson);

    abstract State getWorseState();

    abstract State getBetterState();

    void setName(String aName) {
        name = aName;
    }

    public String getName() {
        return name;
    }

    abstract void changeStateRandomly(Person aPerson, double aPercentageForHealthState, double aPercentageForMediumState);

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) return false;
        State state = (State) obj;
        return getName().equals(state.getName());
    }


    static class Healthy extends State {

        protected Healthy() {
            this(new Random());
        }

        protected Healthy(Random aRandom) {
            rand = aRandom;
            setName(HEALTHY);
        }

        @Override
        void goOutsideFindFood(Person aPerson) {
            //TODO
        }

        @Override
        public void drink(Person aPerson) {
            aPerson.setStrength(aPerson.getStrength() + 1);
        }

        @Override
        public void eat(Person aPerson) {
            aPerson.setStrength(aPerson.getStrength() + 3);
        }

        @Override
        State getWorseState() {
            return new Medium(rand);
        }

        @Override
        State getBetterState() {
            return this;
        }

        @Override
        void changeStateRandomly(Person aPerson, double aPercentageForHealthState, double aPercentageForMediumState) {
        }

        @Override
        public String toString() {
            return getName();
        }

    }


    static class Medium extends State {

        protected Medium() {
            this(new Random());
        }

        protected Medium(Random aRandom) {
            rand = aRandom;
            setName(MEDIUM);
        }

        @Override
        public void goOutsideFindFood(Person aPerson) {
            //TODO
        }

        @Override
        public void drink(Person aPerson) {
            changeStateRandomly(aPerson,0.8, 0.0);
        }

        @Override
        public void eat(Person aPerson) {
            changeStateRandomly(aPerson,0.6, 0.0);
        }

        @Override
        State getWorseState() {
            return new Sick(rand);
        }

        @Override
        State getBetterState() {
            return new Healthy(rand);
        }

        @Override
        void changeStateRandomly(Person aPerson, double aPercentageForHealthState, double aPercentageForMediumState) {
            if (rand.nextDouble() < aPercentageForHealthState) {
                aPerson.setBetterState();
            }
        }

        @Override
        public String toString() {
            return getName();
        }
    }


    static class Sick extends State {

        protected Sick() {
            this(new Random());
        }

        protected Sick(Random aRandom) {
            rand = aRandom;
            setName(SICK);
        }

        @Override
        public void goOutsideFindFood(Person aPerson) {
            //TODO
        }

        @Override
        public void drink(Person aPerson) {
            changeStateRandomly(aPerson, 0.2, 0.6);
        }

        @Override
        public void eat(Person aPerson) {
            changeStateRandomly(aPerson,0.4, 0.4);
        }

        @Override
        State getWorseState() {
            return new Dead(rand);
        }

        @Override
        State getBetterState() {
            return new Medium(rand);
        }

        void changeStateRandomly(Person aPerson, double aPercentageForHigherTwoStates, double aPercentageForHigherState) {
            if (rand.nextDouble() < aPercentageForHigherTwoStates) {
                aPerson.getState().getBetterState();
                aPerson.getState().getBetterState();
            } else if (rand.nextDouble() < aPercentageForHigherState) {
                aPerson.getState().getBetterState();
            }
        }

        @Override
        public String toString() {
            return getName();
        }
    }


    static class Dead extends State {

        protected Dead() {
            this(new Random());
        }

        protected Dead(Random aRandom) {
            rand = aRandom;
            setName(DEAD);
        }

        @Override
        public void goOutsideFindFood(Person aPerson) {
            throw new IllegalStateException("Dead person's state cannot go out, he's dead!");
        }

        @Override
        public void drink(Person aPerson) {
            throw new IllegalStateException("Dead person's state cannot drink!");
        }

        @Override
        public void eat(Person aPerson) {
            throw new IllegalStateException("Dead person cannot eat!");
        }

        @Override
        State getWorseState() {
            return this;
        }

        @Override
        State getBetterState() {
            return new Sick(rand);
        }

        void changeStateRandomly(Person aPerson, double aPercentageForHealthState, double aPercentageForMediumHealth) {
            throw new IllegalStateException("Dead person's state cannot be changed!");
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
