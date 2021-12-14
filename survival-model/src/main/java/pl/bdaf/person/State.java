package pl.bdaf.person;

import java.util.Random;

import static pl.bdaf.person.GameEngine.TOMATO_SOUP;
import static pl.bdaf.person.GameEngine.WATER_BOTTLE;

abstract class State {

    public static final String HEALTHY = "Healthy";
    public static final String UNHEALTHY = "Unhealthy";
    public static final String SICK = "Sick";
    public static final String DEAD = "Dead";

    public static final int MAX_STRENGTH = 33;
    private static final int MAX_HYDRATION = 4;
    private static final int MAX_SATIETY = 7;

    protected Random rand;
    private String name;

    protected State() {
        this(new Random());
    }

    protected State(Random aRandom) {
        rand = aRandom;
    }

    abstract State getWorseState();

    abstract State getBetterState();

    void drink(Person aPerson){
        aPerson.setStrength(Integer.min(aPerson.getStrength()+3, MAX_STRENGTH));
        aPerson.setCheerfulness(aPerson.getCheerfulness()+1);
        aPerson.setHydrationPoints(MAX_HYDRATION);
    }

    void eat(Person aPerson){
        aPerson.setStrength(Integer.min(aPerson.getStrength()+6, MAX_STRENGTH));
        aPerson.setCheerfulness(aPerson.getCheerfulness()+3);
        aPerson.setSatietyPoints(MAX_SATIETY);
        aPerson.setHydrationPoints(Integer.min(aPerson.getHydrationPoints()+1,MAX_HYDRATION));
    }


    void goForExpedition(Person aPerson, Backpack aBackpack){
        int difficultOfExpedition = (int) (rand.nextDouble()*100);
        int strengthOfPerson = aPerson.getStrength()*3;
        aPerson.setStrength(Integer.max(strengthOfPerson/10-2,0));
        aPerson.setCheerfulness(aPerson.getCheerfulness()+5);
        if(difficultOfExpedition <= strengthOfPerson){
            int amountOfTriesToGetSupplies = (strengthOfPerson - difficultOfExpedition)/10+1;
            for (int i = 0; i < amountOfTriesToGetSupplies; i++) {
                if(rand.nextBoolean()) aBackpack.addSupply(WATER_BOTTLE);
                if(rand.nextBoolean()) aBackpack.addSupply(TOMATO_SOUP);
                if(rand.nextInt(difficultOfExpedition) > 50 + strengthOfPerson/4){ // very little chance
                    aPerson.setWorseState();
                }
            }
        } else {
            int chanceToGetWorstState = (difficultOfExpedition - strengthOfPerson);
            if(chanceToGetWorstState > rand.nextInt(100)){
                aPerson.setWorseState();
            }
        }
    }
    
    void setName(String aName) {
        name = aName;
    }

    public String getName() {
        return name;
    }

    Random getRand() {
        return rand;
    }

    abstract void changeStateRandomly(Person aPerson, double aPercentageForHigherTwoStates, double aPercentageForHigherState);

    abstract String getDescribe();

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
        public void drink(Person aPerson) {
            super.drink(aPerson);
            aPerson.setStrength(aPerson.getStrength() + 1);
        }

        @Override
        public void eat(Person aPerson) {
            super.eat(aPerson);
            aPerson.setStrength(aPerson.getStrength() + 1);
        }

        @Override
        State getWorseState() {
            return new Unhealthy(rand);
        }

        @Override
        State getBetterState() {
            return this;
        }

        @Override
        void changeStateRandomly(Person aPerson, double aPercentageForHigherTwoStates, double aPercentageForHigherState) {
        }

        @Override
        String getDescribe() {
            return " is completely healthy.\n";
        }

    }


    static class Unhealthy extends State {

        protected Unhealthy(Random aRandom) {
            rand = aRandom;
            setName(UNHEALTHY);
        }

        @Override
        public void drink(Person aPerson) {
            super.drink(aPerson);
            changeStateRandomly(aPerson,0.0, 0.8);
        }

        @Override
        public void eat(Person aPerson) {
            super.eat(aPerson);
            changeStateRandomly(aPerson,0.0, 0.6);
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
        void changeStateRandomly(Person aPerson, double aPercentageForHigherTwoStates, double aPercentageForHigherState) {
            if (rand.nextDouble() < aPercentageForHigherState) {
                aPerson.setBetterState();
            }
        }

        @Override
        String getDescribe() {
            return " is coughing a little and has a runny nose.\n";
        }
    }


    static class Sick extends State {

        protected Sick(Random aRandom) {
            rand = aRandom;
            setName(SICK);
        }

        @Override
        public void drink(Person aPerson) {
            super.drink(aPerson);
            changeStateRandomly(aPerson, 0.2, 0.6);
        }

        @Override
        public void eat(Person aPerson) {
            super.eat(aPerson);
            changeStateRandomly(aPerson,0.35, 0.45);
        }

        @Override
        State getWorseState() {
            return new Dead(rand);
        }

        @Override
        State getBetterState() {
            return new Unhealthy(rand);
        }

        @Override
        void changeStateRandomly(Person aPerson, double aPercentageForHigherTwoStates, double aPercentageForHigherState) {
            if (rand.nextDouble() < aPercentageForHigherTwoStates) {
                aPerson.setBetterState();
                aPerson.setBetterState();
            } else if (rand.nextDouble() < aPercentageForHigherState) {
                aPerson.setBetterState();
            }
        }

        @Override
        String getDescribe() {
            return " has a high fever and pneumonia!\n";
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
        public void goForExpedition(Person aPerson, Backpack aBackpack) {
            throw new IllegalStateException("Dead person's state cannot go out, he's dead!");
        }

        @Override
        public void drink(Person aPerson) {
            throw new IllegalStateException("Dead person cannot drink!");
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

        @Override
        void changeStateRandomly(Person aPerson, double aPercentageForHigherTwoStates, double aPercentageForHigherState) {
            throw new IllegalStateException("Dead person's state cannot be changed!");
        }

        @Override
        String getDescribe() {
            return " has left in day ";
        }
    }
}
