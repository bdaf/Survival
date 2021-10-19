package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        person = new Person(10, state);
    }

    @Test
    void personShouldGet2StrengthPointByDrinkingTwiceAnd3PointsByEatingBeingHealthy() {
        person.drink();
        assertEquals(11, person.getStrength());
        person.drink();
        assertEquals(12, person.getStrength());
        person.eat();
        assertEquals(15, person.getStrength());
    }

    @Test
    void personShouldGet9StrengthPointByEating3TimesBeingHealthy() {
        person.eat();
        assertEquals(13, person.getStrength());
        person.eat();
        assertEquals(16, person.getStrength());
        person.eat();
        assertEquals(19, person.getStrength());
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

}
