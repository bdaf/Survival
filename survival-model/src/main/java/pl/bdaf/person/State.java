package pl.bdaf.person;

import java.util.Random;

public abstract class State {

    protected Person person;
    protected Random rand;

    protected State(Person aPerson) {
        person = aPerson;
    }

    abstract void goOutsideToFindFood();
    abstract void drink();
    abstract void eat();

    abstract void changeStateRandomly(double aPercentageForHealthState, double aPercentageForMediumState);

    class HealthyState extends State{

        public HealthyState(Person aPerson) {
            super(aPerson);
        }

        @Override
        public void goOutsideToFindFood() {
            //TODO
        }

        @Override
        public void drink() {
            person.setStrength(person.getStrength()+1);
        }

        @Override
        public void eat() {
            person.setStrength(person.getStrength()+3);
        }

        @Override
        void changeStateRandomly(double aPercentageForHealthState, double aPercentageForMediumState) {}
    }

    class MediumState extends State{

        protected MediumState(Person aPerson) {
            super(aPerson);
        }

        @Override
        public void goOutsideToFindFood() {
            //TODO
        }

        @Override
        public void drink() {
            changeStateRandomly(0.8,0.0);
        }

        @Override
        public void eat() {
            changeStateRandomly(0.6,0.0);
        }

        @Override
        void changeStateRandomly(double aPercentageForHealthState, double aPercentageForMediumState) {
            if(rand.nextDouble() < aPercentageForHealthState){
                person.setState(new HealthyState(person));
            }
        }
    }

    class SickState extends State{

        protected SickState(Person aPerson) {
            super(aPerson);
        }

        @Override
        public void goOutsideToFindFood() {
            //TODO
        }

        @Override
        public void drink() {
            changeStateRandomly(0.2,0.6);
        }

        @Override
        public void eat() {
            changeStateRandomly(0.4,0.4);
        }

        void changeStateRandomly(double aPercentageForHealthState, double aPercentageForMediumHealth) {
            double randedNumber = rand.nextDouble();
            if (randedNumber < aPercentageForHealthState) {
                person.setState(new HealthyState(person));
            } else if (randedNumber < aPercentageForMediumHealth) {
                person.setState(new MediumState(person));
            }
        }
    }
}
