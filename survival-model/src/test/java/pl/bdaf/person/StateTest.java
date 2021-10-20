package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.bdaf.person.State.*;

public class StateTest {

    private Random randomize;
    private Person person;
    private State state;

    @BeforeEach
    void init() {
        randomize = new Random();
        randomize = mock(Random.class);
        state = new State.Healthy(randomize);
        person = new Person(PersonStatistic.TO_TEST);
        person.setState(state);
    }

    @Test
    void personShouldGet8StrengthPointByDrinkingTwiceAnd13PointsByEatingBeingHealthy() {
        assertEquals(HEALTHY, person.getState().toString());
        person.drink();
        assertEquals(14, person.getStrength());
        person.drink();
        assertEquals(18, person.getStrength());
        person.eat();
        assertEquals(31, person.getStrength());
    }

    @Test
    void personShouldGet39StrengthPointByEating3TimesBeingHealthy() {
        assertEquals(HEALTHY, person.getState().toString());
        person.eat();
        assertEquals(23, person.getStrength());
        person.eat();
        assertEquals(36, person.getStrength());
        person.eat();
        assertEquals(49, person.getStrength());
    }

    @Test
    void personInMediumStateShouldBecameHealthyByDrinking() {
        person.setWorseState();
        assertEquals(MEDIUM, person.getState().toString());
        when(randomize.nextDouble()).thenReturn(0.8);
        person.drink();
        assertEquals(MEDIUM, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.79);
        person.drink();
        assertEquals(HEALTHY, person.getState().toString());
    }

    @Test
    void personInMediumStateShouldBecameHealthyByEating() {
        person.setWorseState();
        assertEquals(MEDIUM, person.getState().toString());
        when(randomize.nextDouble()).thenReturn(0.6);
        person.eat();
        assertEquals(MEDIUM, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.5);
        person.eat();
        assertEquals(HEALTHY, person.getState().toString());
    }

    @Test
    void personInSickStateShouldBecameHealthyByDrinking() {
        person.setWorseState();
        person.setWorseState();
        assertEquals(SICK, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.6);
        person.drink();
        assertEquals(SICK, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.5);
        person.drink();
        assertEquals(MEDIUM, person.getState().toString());

        person.setWorseState();
        assertEquals(SICK, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.19);
        person.drink();
        assertEquals(HEALTHY, person.getState().toString());
    }

    @Test
    void personInSickStateShouldBecameHealthyByEating() {
        person.setWorseState();
        person.setWorseState();
        assertEquals(SICK, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.45);
        person.eat();
        assertEquals(SICK, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.44);
        person.eat();
        assertEquals(MEDIUM, person.getState().toString());

        person.setWorseState();
        assertEquals(SICK, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.34);
        person.eat();
        assertEquals(HEALTHY, person.getState().toString());
    }

    @Test
    void shouldThrowExceptionWhenDeadPersonTriesToDoAnything() {
        person.setWorseState();
        person.setWorseState();
        person.setWorseState();
        assertEquals(DEAD, person.getState().toString());

        assertEquals(person.getState(), person.getState().getWorseState());
        assertEquals(SICK, person.getState().getBetterState().toString());

        assertThrows(IllegalStateException.class, () -> person.goOutsideFindFood());
        assertThrows(IllegalStateException.class, () -> person.drink());
        assertThrows(IllegalStateException.class, () -> person.eat());
        assertThrows(IllegalStateException.class, () -> person.eat());

    }

    @Test
    void drinkingAndEatingShouldGiveStrengthAndSatietyAndCheerfulnessAndHydrationPoints() {
        assertEquals(HEALTHY, person.getState().toString());

        when(randomize.nextDouble()).thenReturn(0.95);

        person.drink();
        assertEquals(14, person.getStrength());
        assertEquals(11, person.getCheerfulness());
        assertEquals(11, person.getSatietyPoints());
        assertEquals(14, person.getHydrationPoints());

        person.eat();
        assertEquals(27, person.getStrength());
        assertEquals(14, person.getCheerfulness());
        assertEquals(18, person.getSatietyPoints());
        assertEquals(15, person.getHydrationPoints());

        person = new Person(PersonStatistic.TO_TEST);
        person.setState(new State.Medium(randomize));

        person.drink();
        assertEquals(13, person.getStrength());
        assertEquals(11, person.getCheerfulness());
        assertEquals(11, person.getSatietyPoints());
        assertEquals(14, person.getHydrationPoints());

        person.eat();
        assertEquals(23, person.getStrength());
        assertEquals(14, person.getCheerfulness());
        assertEquals(18, person.getSatietyPoints());
        assertEquals(15, person.getHydrationPoints());

        person = new Person(PersonStatistic.TO_TEST);
        person.setState(new State.Medium(randomize));

        person.drink();
        assertEquals(13, person.getStrength());
        assertEquals(11, person.getCheerfulness());
        assertEquals(11, person.getSatietyPoints());
        assertEquals(14, person.getHydrationPoints());

        person.eat();
        assertEquals(23, person.getStrength());
        assertEquals(14, person.getCheerfulness());
        assertEquals(18, person.getSatietyPoints());
        assertEquals(15, person.getHydrationPoints());
    }

    @Test
    void personShouldGoOutAndFindOneWater(){

    }
}
